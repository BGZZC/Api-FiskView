package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.dto.VotoDTO;
import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.service.UsuarioService;
import com.fiskview.apifiskview.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private UsuarioService usuarioService;

    public VotoDTO convertToVotoDTO(Voto voto) {
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setIdUsuario(voto.getUsuario().getId());
        votoDTO.setIdCampana(voto.getCampana().getId());
        votoDTO.setIdCandidato(voto.getCandidato().getId());
        votoDTO.setCodigoHash(voto.getCodigoHash());
        votoDTO.setFechaVoto(voto.getFechaVoto());
        return votoDTO;
    }

    // Crear un nuevo voto
    @PostMapping
    public ResponseEntity<VotoDTO> crearVoto(@RequestBody Voto voto) {
        // Setear fecha actual al momento de registrar el voto
        voto.setFechaVoto(LocalDateTime.now());

        // Dejar el codigoHash vacío
        voto.setCodigoHash("");

        // Guardar el voto
        Voto nuevoVoto = votoService.crearVoto(voto);

        // Convertir el Voto a VotoDTO
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setIdUsuario(nuevoVoto.getUsuario().getId());
        votoDTO.setIdCampana(nuevoVoto.getCampana().getId());
        votoDTO.setIdCandidato(nuevoVoto.getCandidato().getId());
        votoDTO.setCodigoHash(nuevoVoto.getCodigoHash());
        votoDTO.setFechaVoto(nuevoVoto.getFechaVoto());

        // Actualizar el estado del usuario a 0 después de votar
        Long usuarioId = nuevoVoto.getUsuario().getId();
        boolean usuarioActualizado = usuarioService.actualizarEstadoUsuario(usuarioId, 0); // 0 significa que ya votó

        if (usuarioActualizado) {
            return new ResponseEntity<>(votoDTO, HttpStatus.CREATED);  // Retornar VotoDTO
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
