package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.model.Partido;
import com.fiskview.apifiskview.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoRepository partidoRepository;

    @GetMapping
    public List<Partido> getAllPartidos() {
        return partidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> getPartidoById(@PathVariable Long id) {
        return partidoRepository.findById(id)
                .map(partido -> ResponseEntity.ok().body(partido))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Partido createPartido(@RequestBody Partido partido) {
        return partidoRepository.save(partido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partido> updatePartido(@PathVariable Long id, @RequestBody Partido partidoDetails) {
        return partidoRepository.findById(id)
                .map(partido -> {
                    partido.setNombre(partidoDetails.getNombre());
                    // Actualiza otros campos según sea necesario
                    return ResponseEntity.ok().body(partidoRepository.save(partido));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable Long id) {
        if (partidoRepository.existsById(id)) {
            partidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
