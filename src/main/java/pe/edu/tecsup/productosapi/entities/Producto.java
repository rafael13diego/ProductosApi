package pe.edu.tecsup.productosapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productos") 
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;

    private String imagen;

    private String detalles;

    private String estado;

    public Long getId() {
            return id;
    }

    public void setId(Long id) {
            this.id = id;
    }

    public String getNombre() {
            return nombre;
    }

    public void setNombre(String nombre) {
            this.nombre = nombre;
    }

    public Double getPrecio() {
            return precio;
    }

    public void setPrecio(Double precio) {
            this.precio = precio;
    }

    public String getImagen() {
            return imagen;
    }

    public void setImagen(String imagen) {
            this.imagen = imagen;
    }

    public String getDetalles() {
            return detalles;
    }

    public void setDetalles(String detalles) {
            this.detalles = detalles;
    }

    public String getEstado() {
            return estado;
    }

    public void setEstado(String estado) {
            this.estado = estado;
    }

    @Override
    public String toString() {
            return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", imagen=" + imagen + ", detalles="
                            + detalles + ", estado=" + estado + "]";
    }


}
