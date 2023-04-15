package com.ejerciciosmesa.catalogo.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.ejerciciosmesa.catalogo.models.entities.Producto;

public interface ProductoService {
	
	public List<Producto> findAll();
	public Page<Producto> findAll(Pageable pageable);
	public Producto findOne(Long id);
	public void save(Producto producto);
	public void remove(Long id);
	public Long count();
	
	
	
}
