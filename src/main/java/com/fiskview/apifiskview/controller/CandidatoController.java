package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.model.Candidato;
import com.fiskview.apifiskview.repository.CandidatoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/candidatos")
@Validated
public class CandidatoController {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @GetMapping
    public List<Candidato> getAllCandidatos() {
        return candidatoRepository.findAll();
    }

    @GetMapping("/partido/{idPartido}")
    public ResponseEntity<List<Candidato>> getCandidatosByPartido(@PathVariable Long idPartido) {
        List<Candidato> candidatos = candidatoRepository.findByIdPartido(idPartido);
        return ResponseEntity.ok(candidatos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Candidato> getCandidatoById(@PathVariable Long id) {
        return candidatoRepository.findById(id)
                .map(candidato -> ResponseEntity.ok().body(candidato))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidato> createCandidato(@Valid @RequestBody Candidato candidato) {
        Candidato savedCandidato = candidatoRepository.save(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> updateCandidato(@PathVariable Long id, @RequestBody Candidato candidatoDetails) {
        return candidatoRepository.findById(id)
                .map(candidato -> {
                    candidato.setNombres(candidatoDetails.getNombres());
                    candidato.setApellidos(candidatoDetails.getApellidos());
                    candidato.setIdCampana(candidatoDetails.getIdCampana()); // Asegúrate de que esto esté definido
                    candidato.setIdPartido(candidatoDetails.getIdPartido()); // Asegúrate de que esto esté definido
                    candidato.setPropuesta(candidatoDetails.getPropuesta()); // Agrega esto si es necesario
                    candidato.setInformacion(candidatoDetails.getInformacion()); // Agrega esto si es necesario
                    return ResponseEntity.ok().body(candidatoRepository.save(candidato));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidato(@PathVariable Long id) {
        if (candidatoRepository.existsById(id)) {
            candidatoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
