package com.vortexbird.reto.repository;

import com.vortexbird.reto.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    List<Pelicula> findByTituloIgnoreCase(String titulo);
    Pelicula findOneByTituloAndIdPeliculaNot(String titulo, Integer idPelicula);
    Pelicula findByIdPelicula(Integer idPelicula);
}
