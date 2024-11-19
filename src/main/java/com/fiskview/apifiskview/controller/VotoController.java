package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    // Crear un nuevo voto
    @PostMapping
    public ResponseEntity<?> crearVoto(@RequestBody Voto voto) {
        System.out.println(voto.toString());
        String nuevoVoto = votoService.f_insertar_voto(voto);
        return new ResponseEntity<>(nuevoVoto, HttpStatus.CREATED);
    }

    // Obtener todos los votos
    @GetMapping
    public ResponseEntity<List<Voto>> obtenerTodosLosVotos() {
        List<Voto> votos = votoService.obtenerTodosLosVotos();
        return new ResponseEntity<>(votos, HttpStatus.OK);
    }

    // Obtener un voto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Voto> obtenerVotoPorId(@PathVariable("id") Long id) {
        Voto voto = votoService.obtenerVotoPorId(id);
        if (voto != null) {
            return new ResponseEntity<>(voto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar un voto
    @PutMapping("/{id}")
    public ResponseEntity<Voto> actualizarVoto(@PathVariable("id") Long id, @RequestBody Voto voto) {
        Voto votoActualizado = votoService.actualizarVoto(id, voto);
        if (votoActualizado != null) {
            return new ResponseEntity<>(votoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un voto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVoto(@PathVariable("id") Long id) {
        boolean eliminado = votoService.eliminarVoto(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
