package com.vortexbird.reto.service.imp;

import com.vortexbird.reto.entity.Funcion;
import com.vortexbird.reto.entity.Pelicula;
import com.vortexbird.reto.repository.FuncionRepository;
import com.vortexbird.reto.service.IFuncionService;
import com.vortexbird.reto.util.constants.Constants;
import com.vortexbird.reto.util.utilities.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FuncionService implements IFuncionService {

    @Autowired
    private FuncionRepository funcionRepository;

    private final String classLog = getClass().getName() + '.';

    @Override
    public Funcion crearFuncion(Funcion funcion) {
        log.info(Constants.MSN_INICIO_LOG_INFO+classLog+"crearFuncion");
        if(Validation.isNullOrEmpty(funcionRepository.findByPeliculaAndHoraInicio(funcion.getPelicula(), funcion.getHoraInicio()))){
            funcion.setSala(funcion.getSala());
            funcion.setFecha(funcion.getFecha());
            funcion.setHoraInicio(funcion.getHoraInicio());
            funcion.setHoraFin(funcion.getHoraFin());
            funcion.setPrecio(funcion.getPrecio());
            funcion.setPelicula(funcion.getPelicula());
            Funcion newFuncion = funcionRepository.save(funcion);
            if(!Validation.isNullOrEmpty(newFuncion.getSala())){
                return newFuncion;
            }

            log.info(Constants.MSN_FIN_LOG_INFO+classLog+"crearFuncion");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La funcion con esa pelicula y  hora ya existe");
        }

        log.info(Constants.MSN_FIN_LOG_INFO+classLog+"crearFuncion");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo crear la funcion, algo salio mal.");
    }

    @Override
    public Funcion actualizarFuncion(Funcion funcion) {
        log.info(Constants.MSN_INICIO_LOG_INFO+classLog+"actualizarFuncion");
        Optional<Funcion> lista =  funcionRepository.findById(funcion.getIdFuncion());
        if(lista.isPresent()){
            if(Validation.isNullOrEmpty(funcionRepository.findOneByPeliculaAndIdFuncionNot(funcion.getPelicula(),funcion.getIdFuncion()))){
                funcion.setSala(funcion.getSala());
                funcion.setFecha(funcion.getFecha());
                funcion.setHoraInicio(funcion.getHoraInicio());
                funcion.setHoraFin(funcion.getHoraFin());
                funcion.setPrecio(funcion.getPrecio());
                funcion.setPelicula(funcion.getPelicula());
                funcionRepository.save(funcion);
                return funcion;
            }
            log.info(Constants.MSN_FIN_LOG_INFO + classLog + "No se pudo actualizar la funcion");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La funcion ya existe");
        }
        log.info(Constants.MSN_FIN_LOG_INFO+classLog+"actualizarFuncion");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El registro no existe");

    }

    @Override
    public List<Funcion> listarFunciones() {
        List<Funcion> listaFuncion = funcionRepository.findAll();
        if (!Validation.isNullOrEmpty(listaFuncion)) {
            return listaFuncion;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de facultades esta vacia");
    }

    @Override
    public List<Funcion> buscarFuncionesPorPelicula(Integer idPelicula) {
        List<Funcion> funciones = funcionRepository.findAllByPeliculaId(idPelicula);
        if (funciones.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron funciones para la película con ID: " + idPelicula);
        }
        return funciones;
    }

    @Override
    public void eliminarFuncion(Integer idFuncion) {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "eliminarFuncion");
        Optional<Funcion> lista = funcionRepository.findById(idFuncion);
        if (lista.isEmpty()) {
            log.info(Constants.MSN_FIN_LOG_INFO + classLog + "eliminar funcion");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " La funcion no existe");
        }
        funcionRepository.deleteById(idFuncion);
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "eliminarFuncion");
    }
}
