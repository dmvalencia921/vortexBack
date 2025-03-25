package com.vortexbird.reto.security.service;

import org.springframework.security.core.Authentication;

public interface IJwtService {
    String createToken(Authentication authentication);
}
