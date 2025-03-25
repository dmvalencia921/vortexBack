package com.vortexbird.reto.restcontroller;

import com.vortexbird.reto.security.entity.AuthRequest;
import com.vortexbird.reto.security.entity.AuthResponse;
import com.vortexbird.reto.security.entity.UsuarioAuthorizationDto;
import com.vortexbird.reto.security.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/security")
@RequiredArgsConstructor
public class auhtenticateRestController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/authenticate")
    private ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authenticationService.login(authRequest));
    }

    @GetMapping("/info")
    private ResponseEntity<UsuarioAuthorizationDto> info(Principal principal) {
        return ResponseEntity.ok(authenticationService.infoUsuario(principal.getName()));
    }
}
