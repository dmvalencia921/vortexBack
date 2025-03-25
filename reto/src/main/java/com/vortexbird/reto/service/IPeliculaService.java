package com.vortexbird.reto.service;

import com.vortexbird.reto.entity.Pelicula;

import java.util.List;

public interface IPeliculaService {

    Pelicula crearPelicula(Pelicula pelicula);
    Pelicula actualizarPelicula(Pelicula pelicula);
    Pelicula buscarPeliculaPorId(Integer idPelicula);
    List<Pelicula> listarPeliculas();
    void eliminarPelicula(Integer idPelicula);
}
