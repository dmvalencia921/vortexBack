package com.vortexbird.reto.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompra;

    @Column
    private Double total;

    @Column(nullable = false)
    private Date fechaCompra;

    @Column(nullable = false)
    private Integer asientos;

    @ManyToOne
    @JoinColumn(name = "id_funcion", nullable = false)
    private Funcion funcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
