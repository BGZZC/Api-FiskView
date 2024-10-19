package com.fiskview.apifiskview.service;

import com.fiskview.apifiskview.model.UsuarioVotante;
import com.fiskview.apifiskview.repository.UsuarioVotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioVotanteRepository usuariovotanteRepository;

    public UsuarioVotante getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Obtener el nombre de usuario directamente
        Optional<UsuarioVotante> usuarioOptional = usuariovotanteRepository.findByEmail(email);
        return usuarioOptional.orElse(null);
    }
}
