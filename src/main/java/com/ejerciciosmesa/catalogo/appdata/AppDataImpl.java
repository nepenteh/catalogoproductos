package com.ejerciciosmesa.catalogo.appdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.springframework.stereotype.Component;


@Component
public class AppDataImpl  implements AppData {

	private String name; 
	private String author; 
	private int year; 
	private String web; 
	private String webURL; 
	private TreeMap<String,GeneralOption> generalOptions; 
	private ArrayList<GeneralOption> sortedGeneralOptions;
			
	public AppDataImpl() {
		name = "Catálogo de Productos";
		author = "José Manuel Rosado";
		year = 2023;
		web = "ejerciciosmesa.com";
		webURL = "https://ejerciciosmesa.com";
		
		generalOptions = new TreeMap<>();
		sortedGeneralOptions = new ArrayList<>();
		int order=0;
		
		GeneralOption opProductos = new GeneralOption(order,"PRODUCTOS","Gestión de Productos","/productos/list","LIST");

opProductos.addScreen("LIST","Listado de Productos");
opProductos.addScreen("CREATE","Alta de Productos");
opProductos.addScreen("UPDATE","Modificación de Productos");
opProductos.addScreen("VIEW","Ficha del Productos");
opProductos.addScreen("VIEWIMG","Producto - View Image");

opProductos.setEmptyMessage("No hay productos que mostrar");

generalOptions.put("PRODUCTOS",opProductos);
sortedGeneralOptions.add(opProductos);

order++;


		
		Collections.sort(sortedGeneralOptions);
		
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	@Override
	public String getAuthor() {
		return author;
	}

	
	@Override
	public int getYear() {
		return year;
	}

	
	@Override
	public String getWeb() {
		return web;
	}
	
	
	@Override
	public String getWebUrl() {
		return webURL;
	}

	
	@Override
	public String getAuthorship() {
		String authorship = "";
		if(!author.isBlank() || !web.isBlank()) {
			authorship += author+" "+year;
			if(!web.isBlank()) authorship += " - "+web;
		}		
		return authorship.trim();
	}
	
	
	@Override
	public TreeMap<String, GeneralOption> getGeneralOptions() {
		return generalOptions;
	}
	
	@Override
	public ArrayList<GeneralOption> getSortedGeneralOptions() {
		return sortedGeneralOptions;
	}
	
	@Override
	public boolean isMainScreen(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getMainScreenCode().equals(screenCode);
	}
	
	@Override
	public String getMainScreenName(String optionCode) {
		return generalOptions.get(optionCode).getMainScreenName();
	}
	
	@Override
	public String getMainScreenLink(String optionCode) {
		return generalOptions.get(optionCode).getLink();
	}
	
	@Override
	public String getScreenName(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getScreen(screenCode);
	}

	@Override
	public String getEmptyMessage(String optionCode) {
		return generalOptions.get(optionCode).getEmptyMessage();
	}
		
}
