package com.ejerciciosmesa.catalogo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ejerciciosmesa.catalogo.util.paginator.PageRender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.support.SessionStatus;



import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.ejerciciosmesa.catalogo.models.services.UploadService;





import com.ejerciciosmesa.catalogo.appdata.AppData;
import com.ejerciciosmesa.catalogo.models.entities.Producto;
import com.ejerciciosmesa.catalogo.models.services.ProductoService;




@Controller
@SessionAttributes("producto")
@RequestMapping("/productos")
public class ProductoController {

	private final AppData appData;
	private final ProductoService productoService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "PRODUCTOS"; 
	
	public ProductoController(UploadService uploadService,
										 
										 
									     ProductoService productoService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.productoService = productoService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({"","/","/list"})
	public String list(@RequestParam(name="page", defaultValue="0") int page, Model model) {
	
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Producto> pageProducto = productoService.findAll(pageRequest); 
		PageRender<Producto> paginator = new PageRender<>("/productos/list",pageProducto,5);
		

		model.addAttribute("numproducto", productoService.count());
		model.addAttribute("listproducto", pageProducto);
		model.addAttribute("paginator",paginator);
		
		return "productos/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Producto producto = new Producto();		
		model.addAttribute("producto",producto);
		
		fillApplicationData(model,"CREATE");
		
		return "productos/form";
	}
	
	@GetMapping("/form/{id}")
	public String form(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Producto producto = productoService.findOne(id);
		if(producto==null) {
			flash.addFlashAttribute("error","Producto no encontrado");
			return "redirect:/producto/list";
		}
		
		model.addAttribute("producto", producto);
		
		fillApplicationData(model,"UPDATE");
		
		return "productos/form";
	}
	
	
	@PostMapping("/form")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Producto producto,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile image_formname,
@RequestParam("imageImageText") String imageImageText,
@RequestParam("imageImageTextOld") String imageImageTextOld,

					   RedirectAttributes flash,
					   SessionStatus status) {
		
		if(producto.getId()==null)
			fillApplicationData(model,"CREATE");
		else
			fillApplicationData(model,"UPDATE");
		
		String msg = (producto.getId()==null) ? "Producto dado de alta correctamente" : "Producto modificado correctamente";
		
		if(result.hasErrors()) {
			return "productos/form";
		}
		
		if(!image_formname.isEmpty())
	AddUpdateImageImage(image_formname,producto);
else {
	if(imageImageText.isEmpty() && !(imageImageTextOld.isEmpty())) {
		uploadService.delete(imageImageTextOld);
		producto.setImage(null);
	}
}



		
		
		
		productoService.save(producto);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		return "redirect:/productos/list";
	}
	
	
	private void AddUpdateImageImage(MultipartFile image, Producto producto) {
					
			if(producto.getId()!=null &&
			   producto.getId()>0 && 
			   producto.getImage()!=null &&
			   producto.getImage().length() > 0) {
			
				uploadService.delete(producto.getImage());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			producto.setImage(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {
		
		if(id>0) { 			
			Producto producto = productoService.findOne(id);
			
			if(producto != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				productoService.remove(id);
			} else {
				flash.addFlashAttribute("error","Producto no encontrado");
				return "redirect:/productos/list";
			}
			
			if(producto.getImage()!=null)
	uploadService.delete(producto.getImage());

						
			flash.addFlashAttribute("success","Producto eliminado correctamente");
		}
		
		return "redirect:/productos/list";
	}
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Producto producto = productoService.findOne(id);

			if (producto == null) {
				flash.addFlashAttribute("error", "Producto no encontrado");
				return "redirect:/productos/list";
			}

			model.addAttribute("producto", producto);
			fillApplicationData(model, "VIEW");
			return "productos/view";
			
		}

		return "redirect:/productos/list";
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Producto producto = productoService.findOne(id);

			if (producto == null) {
				flash.addFlashAttribute("error", "Producto no encontrado");
				return "redirect:/productos/list";
			}

			model.addAttribute("producto", producto);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "productos/viewimg";
			
		}

		return "redirect:/productos/list";
	}
	
	
	
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}
