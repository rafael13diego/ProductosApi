package pe.edu.tecsup.productosapi.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;

import pe.edu.tecsup.productosapi.entities.Usuario;

@Repository
public class AuthenticationRepository {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Usuario login(String username, String password) throws BadCredentialsException {
		logger.info("login("+username+", "+password+")");
		
		String sql = "select count(*) from usuarios where username=? and password=?";
		
		Integer exists = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
		
		if(exists == 0) {
			throw new BadCredentialsException("Usuario y/o clave invalido");
		}
		
		return findByUsername(username);
	}
	
	public Usuario findByUsername(String username) throws EmptyResultDataAccessException {
		logger.info("findByUsername("+username+")");
		
		String sql = "select * from usuarios where username=?";
		
		Usuario usuario = jdbcTemplate.queryForObject(sql, new RowMapper<Usuario>() {
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setUsername(rs.getString("username"));
				usuario.setNombres(rs.getString("nombres"));
				usuario.setCorreo(rs.getString("correo"));
				usuario.setImagen(rs.getString("imagen"));
				usuario.setEstado(rs.getString("estado"));
				
				return usuario;
			}
		}, username);
		
		logger.info("user: " + usuario);
		
		return usuario;
	}
	
}
