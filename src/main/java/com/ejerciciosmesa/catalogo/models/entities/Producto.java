package com.ejerciciosmesa.catalogo.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.validation.constraints.NotBlank;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.validation.constraints.Size;



@Entity
@Table(name="producto")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Size(max=10)
@Column(name="codigo")
private String codigo;


@NotBlank
@Size(max=50)
@Column(name="descripcion")
private String descripcion;


@NotNull
@Column(name="precio")
private Float precio;


@Column(name="stock")
private Long stock;


@Column(name="tipo")
private String proveedor;


@FutureOrPresent
@DateTimeFormat(pattern = "yyyy-MM-dd")
@Temporal(TemporalType.DATE)
@Column(name="fecha_caducidad")
private Date fechaCaducidad;


@Column(name="image")
private String image;



	
	public Producto() {}


	public Long getId() {
		return id;
	}


	public String getCodigo() {
		return codigo;
}
public void setCodigo(String codigo) {
	this.codigo = codigo;
}
public String getDescripcion() {
		return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public Float getPrecio() {
		return precio;
}
public void setPrecio(Float precio) {
	this.precio = precio;
}
public Long getStock() {
		return stock;
}
public void setStock(Long stock) {
	this.stock = stock;
}
public String getProveedor() {
		return proveedor;
}
public void setProveedor(String proveedor) {
	this.proveedor = proveedor;
}
public Date getFechaCaducidad() {
		return fechaCaducidad;
}
public void setFechaCaducidad(Date fechaCaducidad) {
	this.fechaCaducidad = fechaCaducidad;
}
public String getImage() {
		return image;
}
public void setImage(String image) {
	this.image = image;
}

	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}
