package com.javadabadu.disney.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmConstraints;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    //metodo que controla que la autenticacion sea correcta, si da ok, pasa al siguiente
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user= (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //la palabra debe ir cifrada o como variable de entorno
        String accessToken= JWT.create().withSubject(user.getUsername()) //se debe poner algo unico de la entidad (falta hacer unico el username)
                .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000)) //Asi dura 10 min el token
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken= JWT.create().withSubject(user.getUsername()) //se debe poner algo unico de la entidad (falta hacer unico el username)
                .withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000)) //Asi dura 30 min el token
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
     response.setHeader("accessToken:" ,accessToken);
        response.setHeader("refresToken:" ,refreshToken);
    }
}
