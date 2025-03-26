package com.vortexbird.reto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncion;

    @Column(nullable = false)
    @NotEmpty(message = "La sala no puede ser nula")
    private String sala;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    @NotEmpty(message = "La hora  no puede ser  nula")
    private String horas;

    @Column(nullable = false)
    private Double precio;

    @ManyToOne
    @JoinColumn(name="id_pelicula", nullable = false)
    private Pelicula pelicula;

    @OneToMany(mappedBy = "funcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Compra> listaCompra = new HashSet<>();
}
