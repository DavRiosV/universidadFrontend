package com.drvservicios.universidadFront.dtos;

public class MateriaDTO {
    private Long id;
    private String nombre;
    private AlumnoDTO alumno;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public AlumnoDTO getAlumno() {
		return alumno;
	}
	public void setAlumno(AlumnoDTO alumno) {
		this.alumno = alumno;
	}

   
}
