package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.dto.LoginRequest;
import com.fiskview.apifiskview.model.UsuarioVotante;
import com.fiskview.apifiskview.repository.UsuarioVotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    @Autowired
    private UsuarioVotanteRepository usuariovotanteRepository;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtiene el usuario autenticado desde el servicio de detalles de usuario
            UsuarioVotante usuario = usuariovotanteRepository.findByEmail(loginRequest.getEmail()).orElse(null);

            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado");
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("email", loginRequest.getEmail());
            response.put("userId", String.valueOf(usuario.getId()));

            return response;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Combinación de nombre de usuario/contraseña no válida");
        }
    }

}