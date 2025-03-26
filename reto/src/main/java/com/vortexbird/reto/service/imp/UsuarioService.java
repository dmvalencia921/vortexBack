package com.vortexbird.reto.service.imp;

import com.vortexbird.reto.entity.Usuario;
import com.vortexbird.reto.repository.UsuarioRepository;
import com.vortexbird.reto.service.IUsuarioService;
import com.vortexbird.reto.util.constants.Constants;
import com.vortexbird.reto.util.utilities.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final String classLog = getClass().getName() + '.';

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "crearUsuario");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<Usuario> usuarioExite = usuarioRepository.findByUsuarioIgnoreCase(usuario.getUsuario());
        if (usuarioExite.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }
        String passwordCifrado = passwordEncoder.encode(usuario.getClave());
        Usuario usuarioCrear = new Usuario();
        if (usuario.getUsuario().contains("@gmail.com") || usuario.getUsuario().contains("@yahoo.com")
        || usuario.getUsuario().contains("@hotmail.com") || usuario.getUsuario().contains("@outlook.com")) {
            if(usuario.getUsuario().equals("admin@gmail.com")){
                usuarioCrear.setUsuario(usuario.getUsuario());
                usuarioCrear.setRol(Constants.ADMIN_ROLE);
            }else{
                usuarioCrear.setUsuario(usuario.getUsuario());
                usuarioCrear.setRol(Constants.USER_ROLE);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El usuario debe pertenecer al dominio corecto");
        }
        usuarioCrear.setTelefono(usuario.getTelefono());
        usuarioCrear.setActivo(usuario.isActivo());
        usuarioCrear.setNombres(usuario.getNombres());
        usuarioCrear.setApellidos(usuario.getApellidos());
        usuarioCrear.setClave(passwordCifrado);

        Usuario usuarioCreado = usuarioRepository.save(usuarioCrear);

        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "crearUsuario");
        return usuarioCreado;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "listarUsuarios");
        List<Usuario> usuarioList = usuarioRepository.findAll();
        if (!Validation.isNullOrEmpty(usuarioList)) {
            return usuarioList;
        }
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "listarUsuarios");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de usuarios esta vacía");
    }


    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "actualizarUsuario");
        Optional<Usuario> usuarioExite = usuarioRepository.findById(usuario.getIdUsuario());
        if (usuarioExite.isPresent()) {
            if (!Validation.isNullOrEmpty(
                    usuarioRepository.findOneByUsuarioAndIdUsuarioNot(usuario.getUsuario(), usuario.getIdUsuario()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
            }
        }
        if(usuario.getUsuario().equals("admin@gmail.com")){
            usuarioExite.get().setRol(Constants.ADMIN_ROLE);
        }else{
            usuarioExite.get().setRol(Constants.USER_ROLE);
        }

        usuarioExite.get().setTelefono(usuario.getTelefono());
        usuarioExite.get().setActivo(usuario.isActivo());
        usuarioExite.get().setNombres(usuario.getNombres());
        usuarioExite.get().setApellidos(usuario.getApellidos());
        usuarioExite.get().setUsuario(usuario.getUsuario());

        Usuario usuarioCreado = usuarioRepository.save(usuarioExite.get());


        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "actualizarUsuario");
        return usuarioCreado;
    }

    @Override
    public Usuario buscarUsuarioPorUsuario(String usuario) {
        Usuario usuarioExistente = usuarioRepository.findByUsuario(usuario);
        if (!Validation.isNullOrEmpty(usuarioExistente)) {
            return usuarioExistente;
        }
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "buscarUsuarioPorUsuario");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no exste");
    }


    @Override
    public void eliminarUsuario(Integer idUsuario) {
        log.info(Constants.MSN_INICIO_LOG_INFO + classLog + "eliminarUsuario");
        Optional<Usuario> lista = usuarioRepository.findById(idUsuario);
        if (lista.isEmpty()) {
            log.info(Constants.MSN_FIN_LOG_INFO + classLog + "eliminar usuario");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " El usuario  no existe");
        }
        usuarioRepository.delete(lista.get());
        log.info(Constants.MSN_FIN_LOG_INFO + classLog + "eliminarUsuario");
    }

}
