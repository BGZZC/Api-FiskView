package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.service.FabricService;
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

    @Autowired
    private FabricService fabricService;


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


    @PostMapping("/registrar")
    public String registrarVoto(@RequestParam String votoId,
                                @RequestParam String codigo_hash,
                                @RequestParam String fecha_voto,
                                @RequestParam int campana_id,
                                @RequestParam int candidato_id,
                                @RequestParam int id_usuario) {
        try {
            // Llamada al smart contract para registrar el voto
            String result = fabricService.invokeSmartContract("VotoContract", "registrarVoto", new String[] {
                    votoId, codigo_hash, fecha_voto, String.valueOf(campana_id), String.valueOf(candidato_id), String.valueOf(id_usuario)
            });
            return result;
        } catch (Exception e) {
            return "Error al registrar el voto: " + e.getMessage();
        }
    }

    // Endpoint para obtener un voto por su ID
    @GetMapping("/obtener/{votoId}")
    public Object obtenerVoto(@PathVariable String votoId) {
        try {
            // Llamada al smart contract para obtener el voto
            String result = fabricService.invokeSmartContract("VotoContract", "obtenerVoto", new String[] { votoId });
            return result; // Retorna el voto como JSON o algún formato adecuado
        } catch (Exception e) {
            return "Error al obtener el voto: " + e.getMessage();
        }
    }
}
