package com.vortexbird.reto.service;

import com.vortexbird.reto.entity.Funcion;
import com.vortexbird.reto.entity.Pelicula;

import java.util.List;

public interface IFuncionService {

    Funcion crearFuncion(Funcion funcion);
    Funcion actualizarFuncion(Funcion funcion);
    List<Funcion> listarFunciones();
    Funcion buscarFuncionesPorPelicula(Integer idPelicula);
    void eliminarFuncion(Integer idFuncion);
}
