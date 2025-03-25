package com.vortexbird.reto.security.service;

import com.vortexbird.reto.security.entity.AuthRequest;
import com.vortexbird.reto.security.entity.AuthResponse;
import com.vortexbird.reto.security.entity.UsuarioAuthorizationDto;

public interface IAuthenticationService {
    AuthResponse login(AuthRequest request);
    UsuarioAuthorizationDto infoUsuario(String username);
}
