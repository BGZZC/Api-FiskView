package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.dto.BlockDTO;
import com.fiskview.apifiskview.dto.DetailEventDTO;
import com.fiskview.apifiskview.dto.EventTransaccionDTO;
import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.repository.VotoRepository;
import com.fiskview.apifiskview.service.ContractVoteService;
import com.fiskview.apifiskview.service.EventService;
import com.fiskview.apifiskview.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ContractVoteService contractVoteService;
    @Autowired
    private VotoRepository votoRepository;

    // Crear un nuevo voto
    @PostMapping
    public ResponseEntity<?> crearVoto(@RequestBody Voto voto) throws TransactionException, IOException {
        String hash = votoService.f_insertar_voto(voto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hash);
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


    @GetMapping("/evento/{hash}")
    public ResponseEntity<EventTransaccionDTO> obtenerEventoTransaccion(@PathVariable String hash) throws Exception {
        EventTransaccionDTO response = eventService.obtenerDetallesEventoPorTransaccion(hash);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/evento/detail/{hash}")
    public ResponseEntity<DetailEventDTO> detalleEventoTransaccion(@PathVariable String hash) throws Exception {
        DetailEventDTO response = votoService.obtenerDetalleEventoByHash(hash);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/bloques")
    public ResponseEntity<List<BlockDTO>> listarBloquesYTransacciones() {
        try {
            List<BlockDTO> bloques = contractVoteService.listarBloquesYTransacciones();
            return ResponseEntity.ok(bloques);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/verificar")
    public ResponseEntity<String> verificarHash(@RequestParam String hash) {
        boolean exists = votoRepository.existsByCodigoHash(hash);
        if (exists) {
            return ResponseEntity.ok("Voto encontrado: Gracias por votar.");
        } else {
            return ResponseEntity.ok("Voto no encontrado: No se ha realizado el voto.");
        }
    }
}
