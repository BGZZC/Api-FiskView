package com.fiskview.apifiskview.service;

import com.fiskview.apifiskview.dto.DetailEventDTO;
import com.fiskview.apifiskview.dto.EventTransaccionDTO;
import com.fiskview.apifiskview.dto.VotoRequestDTO;
import com.fiskview.apifiskview.dto.VotoResponseDTO;
import com.fiskview.apifiskview.exception.ResourceNotFoundException;
import com.fiskview.apifiskview.model.Candidato;
import com.fiskview.apifiskview.model.UsuarioVotante;
import com.fiskview.apifiskview.model.Voto;
import com.fiskview.apifiskview.repository.CandidatoRepository;
import com.fiskview.apifiskview.repository.UsuarioVotanteRepository;
import com.fiskview.apifiskview.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final CandidatoRepository candidatoRepository;
    private final UsuarioVotanteRepository usuarioVotanteRepository;
    private final ContractVoteService contractVoteService;
    private final EventService eventService;


    @Transactional
    public VotoResponseDTO registrarVoto(VotoRequestDTO votoRequestDTO) throws IOException, TransactionException {
        if (contractVoteService.hasUserVoted(votoRequestDTO.getCampana_id(), votoRequestDTO.getId_usuario()))
            throw new RuntimeException("El usuario ya ha votado");

        TransactionReceipt transaction = contractVoteService.vote(votoRequestDTO.getCampana_id(),
                votoRequestDTO.getCandidato_id(),
                votoRequestDTO.getId_usuario());


        if (transaction.isStatusOK()) {
            Voto voto =
                    Voto.builder()
                            .id_usuario((int) votoRequestDTO.getId_usuario())
                            .campana_id((int) votoRequestDTO.getCampana_id())
                            .candidato_id((int) votoRequestDTO.getCandidato_id())
                            .codigoHash(transaction.getTransactionHash())
                            .fechaVoto(LocalDateTime.now())
                            .build();
            Voto voteEntity = votoRepository.save(voto);
            return VotoResponseDTO.builder().hashVoto(voteEntity.getCodigoHash()).fechaVoto(voto.getFechaVoto()).build();
        
        throw new TransactionException("Error en el registro de la transacción");
    }


    public List<Voto> obtenerTodosLosVotos() {
        return votoRepository.findAll();
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Voto obtenerVotoPorId(Long id) {
        Optional<Voto> votoOptional = votoRepository.findById(id);
        return votoOptional.orElse(null);
    }

    @Transactional
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
