package com.vortexbird.reto.service.imp;

import com.vortexbird.reto.entity.Compra;
import com.vortexbird.reto.repository.CompraRepository;
import com.vortexbird.reto.service.ICompraService;
import com.vortexbird.reto.util.constants.Constants;
import com.vortexbird.reto.util.utilities.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompraService implements ICompraService {

    @Autowired
    private CompraRepository compraRepository;

    private final String classLog = getClass().getName() + '.';

    @Override
    public Compra crearCompra(Compra compra) {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "crearCompra");

            compra.setAsientos(compra.getAsientos());
            compra.setFuncion(compra.getFuncion());
            compra.setUsuario(compra.getUsuario());
            compra.setTotal(compra.getTotal());
            compra.setFechaCompra(new Date());
            Compra newCompra = compraRepository.save(compra);
            if(!Validation.isNullOrEmpty(newCompra)){
                return newCompra;
            }
        log.info(Constants.MSN_FIN_LOG_INFO+classLog+"crearCompra");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ya existe registro");
    }

    @Override
    public Compra actualizarCompra(Compra compra) {
        log.info(Constants.MSN_INICIO_LOG_INFO+classLog+"actualizarCompra");
        Optional<Compra> lista = compraRepository.findById(compra.getIdCompra());
        if(lista.isPresent()){
            if(Validation.isNullOrEmpty(compraRepository.findOneByIdCompraNot(compra.getIdCompra()))){
                compra.setAsientos(compra.getAsientos());
                compra.setFuncion(compra.getFuncion());
                compra.setUsuario(compra.getUsuario());
                compra.setTotal(compra.getTotal());
                compra.setFechaCompra(new Date());
                compraRepository.save(compra);
                return compra;
            }
            log.info(Constants.MSN_FIN_LOG_INFO + classLog + "No se pudo actualizar la la compra");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La compra ya existe ya existe");
        }
        log.info(Constants.MSN_FIN_LOG_INFO+classLog+"actualizarCompra");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El registro no existe");
    }

    @Override
    public List<Compra> listarCompras() {
        List<Compra> listaCompra = compraRepository.findAll();
        if(!Validation.isNullOrEmpty(listaCompra)){
            return listaCompra;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Lista de facultades esta vacia");
    }

    @Override
    public Optional<Compra> obtenerPorId(Integer idCompra) {
        Optional<Compra> compraExistente = compraRepository.findById(idCompra);

        if (!Validation.isNullOrEmpty(compraExistente)) {
            return compraExistente;
        }
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "buscarUsuarioPorUsuario");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no exste");
    }

    @Override
    public void eliminarCompra(Integer idCompra) {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "eliminarUsuario");
        Optional<Compra> lista = compraRepository.findById(idCompra);
        if (lista.isEmpty()) {
            log.info(Constants.MSN_FIN_LOG_INFO + classLog + "eliminar usuario");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " El usuario  no existe");
        }
        compraRepository.deleteById(idCompra);
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "eliminarUsuario");
    }
}
