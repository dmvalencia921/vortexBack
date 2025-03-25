package com.vortexbird.reto.security.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioAuthorizationDto {
    String username;
    private String roles;
    Integer id;
}
