package com.vortexbird.reto.restcontroller;

import com.vortexbird.reto.entity.Funcion;
import com.vortexbird.reto.entity.Pelicula;
import com.vortexbird.reto.service.imp.FuncionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcion")
public class FuncionRestController {

    @Autowired
    private FuncionService funcionService;


    @PostMapping("/crearFuncion")
    @Operation(summary = "Crear funcion", description = "Metodo que permite crear una funcion")
    public ResponseEntity<Funcion> crearFuncion (@RequestBody  Funcion funcion) {
        return  ResponseEntity.ok(funcionService.crearFuncion(funcion));
    }

    @PutMapping("/actualizarFuncion")
    @Operation(summary = "Actualizar funcion", description = "Metodo que permite actulizar la funcion")
    public ResponseEntity<Funcion> actualizarFuncion (@RequestBody Funcion funcion) {
        return  ResponseEntity.ok(funcionService.actualizarFuncion(funcion));
    }

    @GetMapping("/listarFunciones")
    @Operation(summary = "Listar funciones", description = "Metodo que permite listar todas las funciones")
    public  ResponseEntity<List<Funcion>> listarFunciones () {
        return  ResponseEntity.ok(funcionService.listarFunciones());
    }

    @GetMapping("/buscarPorpelicula/{idPelicula}")
    @Operation(summary = "Buscar por el id de la pelicula" , description = "Metodo que permite buscar la funcion por el id de la pelicula")
    public ResponseEntity<Funcion> buscarFuncionesPorPelicula(@PathVariable  Integer idPelicula) {
        return ResponseEntity.ok(funcionService.buscarFuncionesPorPelicula(idPelicula));
    }

    @DeleteMapping("/eliminarFuncion/{idFuncion}")
    @Operation(summary = "Eliminar funcion", description = "Metodo usado para eliminar el registro de la funcion por su id")
    public void eliminarFuncion (@PathVariable Integer idFuncion) {
       funcionService.eliminarFuncion(idFuncion);
    }
}
