package com.vortexbird.reto.service.imp;

import com.vortexbird.reto.entity.Usuario;
import com.vortexbird.reto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario opUser = usuarioRepository.findByUsuario(username);
        if (opUser == null) {
            throw new UsernameNotFoundException("Usuario no encontrado" + username);
        }

        Usuario user = opUser;
        String authorities = user.getRol();

        return User.builder()
                .username(user.getUsuario())
                .password(user.getClave())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }

}
