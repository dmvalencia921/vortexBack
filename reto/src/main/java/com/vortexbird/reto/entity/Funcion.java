package com.vortexbird.reto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
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
    @NotEmpty(message = "La hora inicio no puede ser  nula")
    private String horaInicio;

    @Column(nullable = false)
    @NotEmpty(message = "La hora fin no puede ser nula")
    private String horaFin;

    @Column(nullable = false)
    private Double precio;

    @ManyToOne
    @JoinColumn(name="id_pelicula", nullable = false)
    private Pelicula pelicula;

    @OneToMany(mappedBy = "funcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Compra> listaCompra = new HashSet<>();
}
