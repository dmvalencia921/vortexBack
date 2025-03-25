package com.vortexbird.reto.service.imp;

import com.vortexbird.reto.entity.Pelicula;
import com.vortexbird.reto.entity.Usuario;
import com.vortexbird.reto.repository.PeliculaRepository;
import com.vortexbird.reto.service.IPeliculaService;
import com.vortexbird.reto.util.constants.Constants;
import com.vortexbird.reto.util.utilities.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
@Slf4j
public class PeliculaService implements IPeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;


    private final String classLog = getClass().getName() + '.';


    @Override
    public Pelicula crearPelicula(Pelicula pelicula) {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "crearPelicula");
        if (Validation.isNullOrEmpty(peliculaRepository.findByTituloIgnoreCase(pelicula.getTitulo()))) {
            pelicula.setTitulo(pelicula.getTitulo());
            pelicula.setDuracion(pelicula.getDuracion());
            pelicula.setGenero(pelicula.getGenero());
            pelicula.setImagen(pelicula.getImagen());
            pelicula.setSinopsis(pelicula.getSinopsis());
            pelicula.setListaFunciones(pelicula.getListaFunciones());

            Pelicula newPelicula = peliculaRepository.save(pelicula);
            if (!Validation.isNullOrEmpty(newPelicula)) {
                return newPelicula;
            }

            log.error(Constants.MSN_FIN_LOG_INFO + classLog + "No se pudo crear la pelicula");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo crear la pelicula");
        }
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "crearPelicula");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La pelicula ya existe");
    }

    @Override
    public Pelicula actualizarPelicula(Pelicula pelicula) {
        log.info(Constants.MSN_INICIO_LOG_INFO+classLog+"actualizarPelicula");
        Optional<Pelicula> lista = peliculaRepository.findById(pelicula.getIdPelicula());
        if(lista.isPresent()){
            if(Validation.isNullOrEmpty(peliculaRepository.findOneByTituloAndIdPeliculaNot(pelicula.getTitulo(), pelicula.getIdPelicula()))){
                System.out.println("enteeeeeee"+pelicula.getTitulo());
                pelicula.setTitulo(pelicula.getTitulo());
                pelicula.setDuracion(pelicula.getDuracion());
                pelicula.setGenero(pelicula.getGenero());
                pelicula.setImagen(pelicula.getImagen());
                pelicula.setSinopsis(pelicula.getSinopsis());
                peliculaRepository.save(pelicula);
                return pelicula;
            }
            log.info(Constants.MSN_FIN_LOG_INFO + classLog + "No se pudo actualizar la pelicula");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La facutad ya existe");
        }
        log.info(Constants.MSN_FIN_LOG_INFO+classLog+"actualizarPelicula");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El registro no existe");
    }

    @Override
    public Pelicula buscarPeliculaPorId(Integer idPelicula) {
        Pelicula peliculaExistente = peliculaRepository.findByIdPelicula(idPelicula);
        if (!Validation.isNullOrEmpty(peliculaExistente)) {
            return peliculaExistente;
        }
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "buscarPeliculaPorId");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La pelicula no exste");
    }

    @Override
    public List<Pelicula> listarPeliculas() {
        List<Pelicula> listaPelicula =  peliculaRepository.findAll();
        if(!Validation.isNullOrEmpty(listaPelicula)){
            return listaPelicula;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Lista de facultades esta vacia");
    }
    @Override
    public void eliminarPelicula(Integer idPelicula) {

        log.info(Constants.MSN_INICIO_LOG_INFO+classLog+"eliminarFacultad");
        Optional<Pelicula> lista = peliculaRepository.findById(idPelicula);
        if(lista.isEmpty()){
            log.info(Constants.MSN_FIN_LOG_INFO+classLog + "eliminarFacultad");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La facultad no existe");
        }
        peliculaRepository.deleteById(idPelicula);
        log.info(Constants.MSN_FIN_LOG_INFO+classLog+"eliminarFacultad");

    }
}
