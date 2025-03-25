package com.vortexbird.reto.restcontroller;

import com.vortexbird.reto.entity.Usuario;
import com.vortexbird.reto.service.imp.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/crearUsuario")
    @Operation(summary = "crear usuario" , description = "Metodo usado para crear ususario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return  ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    @GetMapping("/listarUsuarios")
    @Operation(summary = "listar usuarios", description = "Metodo encargado de listar los usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return  ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/buscarPorUsuario/{usuario}")
    @Operation(summary = "buscar por usuario", description = "Metodo encargado de buscar un usuario por su usuario")
    public ResponseEntity<Usuario> buscarUsuarioPorUsuario(@PathVariable  String usuario) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorUsuario(usuario));
    }

    @PutMapping("/actualizarUsuario")
    @Operation(summary = "actualizar usuario", description = "Metodo usado para actualizar usuario")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        return  ResponseEntity.ok(usuarioService.actualizarUsuario(usuario));
    }

    @DeleteMapping("/eliminarUsuario/{idUsuario}")
    @Operation(summary = "eliminar usuario", description = "Metodo usado para eliminar un usuario por el id")
    public void eliminarUsuario(@PathVariable Integer idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
    }
}
