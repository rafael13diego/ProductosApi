package pe.edu.tecsup.productosapi.entities;


public class Usuario {
    
    private Integer id;
	
    private String username;

    private String password;

    private String nombres;

    private String correo;

    private String imagen;

    private String estado;

    private String token;

    public Integer getId() {
            return id;
    }

    public void setId(Integer id) {
            this.id = id;
    }

    public String getUsername() {
            return username;
    }

    public void setUsername(String username) {
            this.username = username;
    }

    public String getPassword() {
            return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public String getNombres() {
            return nombres;
    }

    public void setNombres(String nombres) {
            this.nombres = nombres;
    }

    public String getCorreo() {
            return correo;
    }

    public void setCorreo(String correo) {
            this.correo = correo;
    }

    public String getImagen() {
            return imagen;
    }

    public void setImagen(String imagen) {
            this.imagen = imagen;
    }

    public String getEstado() {
            return estado;
    }

    public void setEstado(String estado) {
            this.estado = estado;
    }

    public String getToken() {
            return token;
    }

    public void setToken(String token) {
            this.token = token;
    }

    @Override
    public String toString() {
            return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", nombres=" + nombres
                            + ", correo=" + correo + ", imagen=" + imagen + ", estado=" + estado + "]";
    }


}
