package io.github.cursodsousa.assembleiaapi.application.voto;

import io.github.cursodsousa.assembleiaapi.application.pauta.PautaService;
import io.github.cursodsousa.assembleiaapi.domain.exception.StatusEntidadeInvalidoException;
import io.github.cursodsousa.assembleiaapi.domain.exception.RegistroDuplicadoException;
import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/pautas/{id}/votos")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Votos")
public class VotoController {

    private final PautaService pautaService;
    private final VotoService votoService;

    @PostMapping
    @Operation(summary = "Registrar Voto",  description = "Registra o voto de um associado para uma pauta")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Voto registrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta inexistente"),
            @ApiResponse(responseCode = "422", description = "Pauta ainda fora de sessão"),
            @ApiResponse(responseCode = "409", description = "Voto já registrado anteriomente para esta pauta e associado")
    })
    public ResponseEntity registrarVoto(@PathVariable @ApiParam("id da pauta") Long id, @RequestBody @Valid @ApiParam("voto") RegistroVotoRequest votoRequest) {
        Optional<Pauta> possivelPauta = pautaService.obterPorId(id);

        if(possivelPauta.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        try {
            votoService.registrarVoto(possivelPauta.get(), votoRequest.getCpf(), votoRequest.getOpcao());
            return ResponseEntity.accepted().build();
        } catch (StatusEntidadeInvalidoException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
