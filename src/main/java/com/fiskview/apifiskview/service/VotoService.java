package com.fiskview.apifiskview.service;

import com.fiskview.apifiskview.dto.VotoDTO;
import com.fiskview.apifiskview.model.Campana;
import com.fiskview.apifiskview.model.Candidato;
import com.fiskview.apifiskview.model.UsuarioVotante;
import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.repository.CampanaRepository;
import com.fiskview.apifiskview.repository.CandidatoRepository;
import com.fiskview.apifiskview.repository.UsuarioVotanteRepository;
import com.fiskview.apifiskview.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private UsuarioVotanteRepository usuarioRepository;  // Repositorio para acceder a la tabla de usuarios
    @Autowired
    private CampanaRepository campanaRepository;
    @Autowired
    private CandidatoRepository candidatoRepository;
    @Autowired
    private VotoRepository votoRepository;

    public Voto crearVoto(VotoDTO votoDTO) {
        // Verificamos que el usuario existe en la base de datos
        UsuarioVotante usuario = usuarioRepository.findById(votoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificamos que la campaña y el candidato existen en la base de datos
        Campana campana = campanaRepository.findById(votoDTO.getIdCampana())
                .orElseThrow(() -> new RuntimeException("Campaña no encontrada"));
        Candidato candidato = candidatoRepository.findById(votoDTO.getIdCandidato())
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));

        // Crear un nuevo voto y asignar los valores
        Voto voto = new Voto();
        voto.setUsuario(usuario);         // Asignamos el objeto UsuarioVotante
        voto.setCampana(campana);         // Asignamos el objeto Campana
        voto.setCandidato(candidato);     // Asignamos el objeto Candidato
        voto.setCodigoHash(votoDTO.getCodigoHash());
        voto.setFechaVoto(votoDTO.getFechaVoto());

        // Guardamos el voto en la base de datos
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
