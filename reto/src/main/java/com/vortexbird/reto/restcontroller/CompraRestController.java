package com.vortexbird.reto.restcontroller;

import com.vortexbird.reto.entity.Compra;
import com.vortexbird.reto.service.imp.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compra")
public class CompraRestController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/crearCompra")
    @Operation(summary = "Crear compra", description = "Metodo que permite la creacion de una compra")
    public ResponseEntity<Compra> crearCompra (@RequestBody Compra compra) {
        return  ResponseEntity.ok(compraService.crearCompra(compra));
    }

    @PutMapping("/actualizarCompra")
    @Operation(summary = "Actualizar compra", description = "Metodo que permite la actuzalizacion de una compra")
    public ResponseEntity<Compra> actualizarCompra (@RequestBody Compra compra) {
        return  ResponseEntity.ok(compraService.actualizarCompra(compra));
    }

    @GetMapping("/listarCompras")
    @Operation(summary = "Listar compras", description = "Metodo que permite lsitar las compras")
    public ResponseEntity<List<Compra>> listarCompras () {
        return  ResponseEntity.ok(compraService.listarCompras());
    }

    @DeleteMapping("/eliminarCompra/{idCompra}")
    @Operation(summary = "Eliminar compra", description = "Meotod que permite eliminar una compra por si id")
    public void  eliminarCompra (Integer idCompra) {
        compraService.eliminarCompra(idCompra);
    }
}
