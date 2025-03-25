package com.vortexbird.reto.service;

import com.vortexbird.reto.entity.Compra;

import java.util.List;
import java.util.Optional;

public interface ICompraService {

    Compra crearCompra(Compra compra);
    Compra actualizarCompra(Compra compra);
    List<Compra> listarCompras();
    Optional<Compra> obtenerPorId(Integer idCompra);
    void eliminarCompra(Integer idCompra);
}
