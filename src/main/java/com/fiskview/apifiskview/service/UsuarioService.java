package com.fiskview.apifiskview.service;

import com.fiskview.apifiskview.model.UsuarioVotante;
import com.fiskview.apifiskview.repository.UsuarioVotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioVotanteRepository usuarioRepository;

    public boolean actualizarEstadoUsuario(Long usuarioId, int nuevoEstado) {
        Optional<UsuarioVotante> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioVotante usuario = usuarioOpt.get();
            usuario.setEstado(nuevoEstado); // Asumiendo que 'estado' es un campo de tipo entero
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}
