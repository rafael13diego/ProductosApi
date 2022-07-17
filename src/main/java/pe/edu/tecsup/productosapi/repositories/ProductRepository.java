package pe.edu.tecsup.productosapi.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import pe.edu.tecsup.productosapi.entities.Producto;

public interface ProductRepository extends CrudRepository<Producto, Long>{

    List<Producto> findAll();
    
}
