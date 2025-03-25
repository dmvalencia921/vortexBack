package com.vortexbird.reto.repository;

import com.vortexbird.reto.entity.Funcion;
import com.vortexbird.reto.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Integer> {

    Funcion findByPeliculaAndHoraInicio(Pelicula pelicula, String horaInicio);

    Funcion findOneByPeliculaAndIdFuncionNot(Pelicula pelicula, Integer idFuncion);

    @Query("SELECT f FROM Funcion f WHERE f.pelicula.idPelicula = :idPelicula")
    List<Funcion> findAllByPeliculaId(Integer idPelicula);
}
