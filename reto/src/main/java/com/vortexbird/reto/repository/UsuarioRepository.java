package com.vortexbird.reto.repository;

import com.vortexbird.reto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {

    Usuario findOneByUsuarioAndIdUsuarioNot(String usuario, Integer idUsuario);

    Optional<Usuario> findByUsuarioIgnoreCase(String usuario);

    Usuario findByUsuario(String usuario);
}
