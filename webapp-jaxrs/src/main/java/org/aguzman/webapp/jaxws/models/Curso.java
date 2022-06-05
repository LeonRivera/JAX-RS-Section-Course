package org.aguzman.webapp.jaxws.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement //for a XML response
@Entity
@Table(name = "cursos")
public class Curso{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    
    // @JsonbTransient //not includes Instructor in the json
    // @JsonIgnore //same as up
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JoinColumn(name = "instructor")  //by default it tries to search for curso.instructor_id and gets error
    private Instructor instructor;


    private Double duracion;

    public Curso() {
    }

    

    public Curso(String nombre) {
        this.nombre = nombre;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getDescripcion() {
        return descripcion;
    }



    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



   



    public Instructor getInstructor() {
        return instructor;
    }



    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }



    public Double getDuracion() {
        return duracion;
    }



    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    
}
