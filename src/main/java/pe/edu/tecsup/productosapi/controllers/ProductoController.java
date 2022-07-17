package pe.edu.tecsup.productosapi.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.tecsup.productosapi.entities.ApiMessage;

import pe.edu.tecsup.productosapi.entities.Producto;
import pe.edu.tecsup.productosapi.services.ProductoService;


@RestController
@RequestMapping("/api")
public class ProductoController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
	
    @Value("${app.storage.path}")
    private String STORAGEPATH;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public List<Producto> productos() {
        logger.info("call productos");

        List<Producto> productos = productoService.findAll();
        logger.info("productos: " + productos);

        return productos;
    }
    
    @GetMapping("/productos/name/{nombre}")
    public List<Producto> findByName(@PathVariable String nombre){
        List<Producto> p = new ArrayList<Producto>();
        List<Producto> todos = productoService.findAll();
        for(Producto prod: todos){
            prod.setNombre(prod.getNombre().toLowerCase());
            if(prod.getNombre().contains(nombre)){
                p.add(prod);
                System.out.println(p);
            }
        }
        return p;
    }

    @GetMapping("/productos/images/{filename:.+}")
    public ResponseEntity<Resource> files(@PathVariable String filename) throws Exception{
        logger.info("call images: " + filename);

        Path path = Paths.get(STORAGEPATH).resolve(filename);
        logger.info("Path: " + path);

        if(!Files.exists(path)) {
                return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(path.toUri());
        logger.info("Resource: " + resource);

        return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+resource.getFilename()+"\"")
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(STORAGEPATH).resolve(filename)))
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
                        .body(resource);
    }
    
    @PostMapping("/productos")
    public Producto crear(@RequestParam(name="imagen", required=false) MultipartFile imagen, @RequestParam("nombre") String nombre, @RequestParam("precio") Double precio, @RequestParam("detalles") String detalles) throws Exception {
        logger.info("call crear(" + nombre + ", " + precio + ", " + detalles + ", " + imagen + ")");

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDetalles(detalles);
        producto.setEstado("1");

        if (imagen != null && !imagen.isEmpty()) {
                String filename = System.currentTimeMillis() + imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
                producto.setImagen(filename);
                if(Files.notExists(Paths.get(STORAGEPATH))){
                Files.createDirectories(Paths.get(STORAGEPATH));
            }
                Files.copy(imagen.getInputStream(), Paths.get(STORAGEPATH).resolve(filename));
        }

        productoService.save(producto);

        return producto;
    }
    
    @DeleteMapping("/productos/{id}")
    public ApiMessage eliminar(@PathVariable Long id) {
            logger.info("call eliminar: " + id);

            productoService.deleteById(id);

            return ApiMessage.createMessage("Registro eliminado");
    }

    @GetMapping("/productos/{id}")
	public Producto obtener(@PathVariable Long id) {
		logger.info("call obtener: " + id);
		
		Producto producto = productoService.findById(id);
		
		return producto;
	}

}
