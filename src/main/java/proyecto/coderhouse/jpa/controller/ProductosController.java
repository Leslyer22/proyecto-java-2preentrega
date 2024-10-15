package proyecto.coderhouse.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.coderhouse.jpa.entity.Productos;
import proyecto.coderhouse.jpa.service.ProductosService;

import java.util.Optional;

@RestController
@RequestMapping("/api/productos")  // Ruta base para los productos
public class ProductosController {

    @Autowired
    private ProductosService service;

    public ProductosController(ProductosService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Productos>> getAll() {
        Iterable<Productos> productos = service.getProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Productos>> getById(@PathVariable Integer id) {
        Optional<Productos> producto = service.getById(id);
        if (producto.isPresent()) {

            return ResponseEntity.ok(producto);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Productos> create(@RequestBody Productos producto) {
        try {
            Productos nuevoProducto = service.save(producto);
            return ResponseEntity.ok(nuevoProducto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

