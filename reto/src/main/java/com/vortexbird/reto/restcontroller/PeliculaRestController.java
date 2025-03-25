package com.vortexbird.reto.restcontroller;

import com.vortexbird.reto.entity.Pelicula;
import com.vortexbird.reto.service.imp.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/pelicula")
public class PeliculaRestController {

    @Autowired
    private PeliculaService peliculaService;

    @PostMapping("/crearPelicula")
    @Operation(summary = "Crear pelicula", description = "Metodo que permite crear una pelicula")
    public ResponseEntity<Pelicula> crearPelicula (@RequestBody Pelicula pelicula) {
        return ResponseEntity.ok(peliculaService.crearPelicula(pelicula));
    }

    @PutMapping("/actualizarPelicula")
    @Operation(summary = "Actualizar pelicula", description = "Metodo que permite actualizar la informacion de una pelicula")
    public ResponseEntity<Pelicula> actualizarPelicula (@RequestBody Pelicula pelicula) {
        return ResponseEntity.ok(peliculaService.actualizarPelicula(pelicula));
    }

    @GetMapping("/listarPeliculas")
    @Operation(summary = "listar peliculas", description = "Metodo que permite listar as peliculas creadas")
    public ResponseEntity<List<Pelicula>> listarPeliculas () {
        return ResponseEntity.ok(peliculaService.listarPeliculas());
    }

    @GetMapping("/buscarPeliculaPorId/{idPelicula}")
    @Operation(summary = "Buscar por id pelicula ", description = "Metodo que permite buscar una pelicula por su id")
    public ResponseEntity<Pelicula> buscarPeliculaPorIdPelicula(@PathVariable  Integer idPelicula) {
        return  ResponseEntity.ok(peliculaService.buscarPeliculaPorId(idPelicula));
    }

    @DeleteMapping("/eliminarPelicual/{idPelicula}")
    @Operation(summary = "Eliminar pelicula", description = "Metodo que permite eliminar una pelicula por su id")
    public void eliminarPelicula (@PathVariable Integer idPelicula) {
        peliculaService.eliminarPelicula(idPelicula);
    }
}
