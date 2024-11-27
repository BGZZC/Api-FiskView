package com.fiskview.apifiskview.service;

import com.fiskview.apifiskview.dto.DetailEventDTO;
import com.fiskview.apifiskview.dto.EventTransaccionDTO;
import com.fiskview.apifiskview.exception.ResourceNotFoundException;
import com.fiskview.apifiskview.model.Candidato;
import com.fiskview.apifiskview.model.UsuarioVotante;
import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.repository.CandidatoRepository;
import com.fiskview.apifiskview.repository.UsuarioVotanteRepository;
import com.fiskview.apifiskview.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;
    @Autowired
    private UsuarioVotanteRepository usuarioVotanteRepository;
    @Autowired
    private ContractVoteService contractVoteService;
    @Autowired
    private EventService eventService;

    public Voto crearVoto(Voto voto) {
        return votoRepository.save(voto);
    }

    public String f_insertar_voto(Voto voto) throws TransactionException, IOException {
        // Validar que el usuario no haya votado
        if (contractVoteService.hasUserVoted((long) voto.getCampana_id(), (long) voto.getId_usuario())) {
            throw new RuntimeException("El usuario ya ha votado");
        }

        // Realizar la transacción en la blockchain
        TransactionReceipt transaction = contractVoteService.vote(
                (long) voto.getCampana_id(),
                (long) voto.getCandidato_id(),
                (long) voto.getId_usuario()
        );

        // Verificar si la transacción fue correcta
        if (transaction.isStatusOK()) {
            // Obtener el hash de la transacción
            String transactionHash = transaction.getTransactionHash();

            // Insertar en la base de datos llamando al procedimiento almacenado
            votoRepository.f_insertar_voto(
                    voto.getId_usuario(),
                    voto.getCampana_id(),
                    voto.getCandidato_id(),
                    transactionHash
            );

            // Retornar el hash para el controlador
            return transactionHash;
        }

        // Si la transacción no fue exitosa, lanzar excepción
        throw new TransactionException("Error en el registro de la transacción");
    }


    public List<Voto> obtenerTodosLosVotos() {
        return votoRepository.findAll();
    }

    public DetailEventDTO obtenerDetalleEventoByHash(String hash) throws Exception {
        EventTransaccionDTO event = eventService.obtenerDetallesEventoPorTransaccion(hash);
        if (event == null)
            throw new RuntimeException("Evento no encontrado: Detalle Evento");
        Candidato candidato =
                candidatoRepository.findById(event.getCandidateId()).orElseThrow(() -> new ResourceNotFoundException(
                        "Candidato no encontrado"));
        UsuarioVotante usuarioVotante =
                usuarioVotanteRepository.findById(event.getUserId()).orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado"));
        return DetailEventDTO.builder().candidato(candidato).usuario(usuarioVotante).fecha(LocalDateTime.now()).transactionHash(hash).build();
    }

    public Voto obtenerVotoPorId(Long id) {
        Optional<Voto> votoOptional = votoRepository.findById(id);
        return votoOptional.orElse(null);
    }

    public Voto actualizarVoto(Long id, Voto voto) {
        if (votoRepository.existsById(id)) {
            voto.setId(id);
            return votoRepository.save(voto);
        }
        return null;
    }

    public void listarBloques() throws Exception {
        contractVoteService.listarBloquesYTransacciones();
    }

    public boolean eliminarVoto(Long id) {
        if (votoRepository.existsById(id)) {
            votoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
