package pe.edu.tecsup.productosapi.filter;
import io.jsonwebtoken.JwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.edu.tecsup.productosapi.entities.Usuario;
import pe.edu.tecsup.productosapi.services.AuthenticationService;
import pe.edu.tecsup.productosapi.services.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private RequestMatcher requestMatcher;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtTokenService jwtTokenService;

    public JwtAuthenticationTokenFilter(String pattrern) {
        this.requestMatcher = new AntPathRequestMatcher(pattrern);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        if(requestMatcher.matches(request)) {

        	logger.info("Filter API:" + request.getRequestURI());

            String authToken = request.getHeader(JwtTokenService.HEADER_AUTHORIZATION);
            logger.info("authToken " + authToken);

            if (authToken == null) {
            	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
                return;
            }
            
            try {

            	// Decode username from token
                String username = jwtTokenService.parseToken(authToken);
                logger.info("checking authentication for user " + username + " with permission to " + request.getRequestURI());

                // Getting user information from database
                Usuario user = authenticationService.findByUsername(username);
                logger.info("user " + user);

                // Save user to spring security session
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("authenticated user " + username + ", setting security context");
                
            }catch (JwtException e){
            	logger.warn(e.getMessage(), e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }

        }

        chain.doFilter(request, response);
    }

}
