package com.vortexbird.reto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;


    @Column(nullable = false)
    @NotEmpty(message = "El nombre no puede ser nulo")
    private String nombres;

    @Column(nullable = false)
    @NotEmpty(message = "El apellido no puede ser nulo")
    private String apellidos;

    @Column(nullable = false)
    @NotEmpty(message = "El telefon no puede ser nulo")
    private String telefono;


    @Column(nullable = false)
    @NotEmpty(message = "El usuario no puede ser nulo")
    private String usuario;

    @Column(nullable = false)
    @NotEmpty(message = "La contasena  no puede ser nulo")
    private String clave;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    @NotEmpty(message = "El rol  no puede ser nulo")
    private String rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Compra> listaCompra = new HashSet<>();
}


