package com.ejerciciosmesa.catalogo.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.ejerciciosmesa.catalogo.models.dao.ProductoDAO;
import com.ejerciciosmesa.catalogo.models.entities.Producto;


@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoDAO productoDAO;
	
	
	
	public ProductoServiceImpl(
			
			ProductoDAO productoDAO
			) {
		
		this.productoDAO = productoDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Producto> findAll() {
		return (List<Producto>) productoDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Producto> findAll(Pageable pageable) {
		return productoDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Producto findOne(Long id) {
		return productoDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Producto producto) {
		productoDAO.save(producto);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		productoDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return productoDAO.count();
	}
	
	
	
	
}
