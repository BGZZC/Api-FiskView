package com.fiskview.apifiskview.service;

import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    public Voto crearVoto(Voto voto) {
        return votoRepository.save(voto);
    }

    public List<Voto> obtenerTodosLosVotos() {
        return votoRepository.findAll();
    }

    public Voto obtenerVotoPorId(Long id) {
        Optional<Voto> votoOptional = votoRepository.findById(id);
        return votoOptional.orElse(null);
    }

    public Voto actualizarVoto(Long id, Voto voto) {
        if (votoRepository.existsById(id)) {
            voto.setIdVoto(id);
            return votoRepository.save(voto);
        }
        return null;
    }

    public boolean eliminarVoto(Long id) {
        if (votoRepository.existsById(id)) {
            votoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
