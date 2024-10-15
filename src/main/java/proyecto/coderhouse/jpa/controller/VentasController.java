package proyecto.coderhouse.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.coderhouse.jpa.entity.Ventas;
import proyecto.coderhouse.jpa.service.VentasService;

import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")  // Ruta base para las ventas
public class VentasController {

    @Autowired
    private VentasService service;

    public VentasController(VentasService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Ventas>> getAll() {
        Iterable<Ventas> ventas = service.getVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Ventas>> getById(@PathVariable Integer id) {
        Optional<Ventas> venta = service.getById(id);
        if (venta.isPresent()) {
            // Si la venta existe
            return ResponseEntity.ok(venta);
        } else {
            // Si la venta no existe
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Ventas> create(@RequestBody Ventas venta) {
        try {
            Ventas nuevaVenta = service.save(venta);
            return ResponseEntity.ok(nuevaVenta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

