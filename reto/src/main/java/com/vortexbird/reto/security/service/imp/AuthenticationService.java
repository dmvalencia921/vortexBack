package com.vortexbird.reto.security.service.imp;

import com.vortexbird.reto.entity.Usuario;
import com.vortexbird.reto.security.entity.AuthRequest;
import com.vortexbird.reto.security.entity.AuthResponse;
import com.vortexbird.reto.security.entity.UsuarioAuthorizationDto;
import com.vortexbird.reto.security.service.IAuthenticationService;
import com.vortexbird.reto.security.service.IJwtService;
import com.vortexbird.reto.service.imp.UsuarioService;
import com.vortexbird.reto.util.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final IJwtService tokenProvider;

    @Override
    public AuthResponse login(AuthRequest request) {
        // Aqui se llama a @Bean public AuthenticationProvider en securityConfiguration.
        // En este caso es MphUserDetailService
        Authentication au = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String jwt = tokenProvider.createToken(au);
        UsuarioAuthorizationDto userDTO = infoUsuario(request.getUsername());

        List<String> roles = au.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return AuthResponse.builder().id(userDTO.getId()).userName(request.getUsername()).roles(roles).token(jwt)
                .build();
    }

    @Override
    public UsuarioAuthorizationDto infoUsuario(String username) {
        Usuario usuarioExistente = usuarioService.buscarUsuarioPorUsuario(username);
        String roles = usuarioExistente.getRol();

        boolean isAdmin = roles.equals(Constants.ADMIN_ROLE);

        UsuarioAuthorizationDto usuarioResp = UsuarioAuthorizationDto.builder().username(username).roles(roles)
                .id(usuarioExistente.getIdUsuario()).build();
        return usuarioResp;
    }
}
