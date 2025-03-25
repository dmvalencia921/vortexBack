package com.vortexbird.reto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPelicula;

    @Column(nullable = false)
    @NotEmpty(message = "El titulo no puede ser nulo")
    private String titulo;

    @Column(nullable = false, length = 1000)
    @NotEmpty(message = "La sinopsis no puede ser nulo")
    private String sinopsis;

    @Column(nullable = false)
    private Integer duracion;

    @Column(nullable = false)
    @NotEmpty(message = "El genero no puede ser nulo")
    private String genero;

    @Column(nullable = false)
    @NotEmpty(message = "Es necesaria una imagen")
    private String imagen;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Funcion> listaFunciones = new HashSet<>();
}
