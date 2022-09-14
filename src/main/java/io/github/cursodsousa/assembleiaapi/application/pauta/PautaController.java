package io.github.cursodsousa.assembleiaapi.application.pauta;

import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/v1/pautas")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Pautas")
public class PautaController {

    private final PautaService service;

    @PostMapping
    @Operation(summary = "Criar uma pauta",  description = "Cria uma pauta que poderá ser colocada em sessão")
    @ApiResponses({
        @ApiResponse(responseCode = "422", description = "Erro na introdução dos dados"),
        @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso")
    })
    public ResponseEntity criar(@RequestBody @Valid @Schema(description = "pauta") NovaPautaRequest resource, UriComponentsBuilder uriComponentsBuilder) {
        Pauta pauta = resource.toModel();
        service.criar(pauta);

        URI location = uriComponentsBuilder.path("{id}").buildAndExpand(pauta.getId()).toUri();
        log.info("Nova pauta criada: {}", pauta.getDescricao());

        return ResponseEntity.created(location).build();
    }

    @PostMapping("{id}/sessao")
    @Operation(summary = "Iniciar Sessão", description = "Inicia a sessão para uma pauta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão iniciada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
            @ApiResponse(responseCode = "409", description = "Pauta já iniciada anteriomente")
    })
    public ResponseEntity iniciarSessao(@PathVariable @ApiParam("id da pauta") Long id, @RequestBody @ApiParam("sessao") NovaSessaoRequest sessao) {
        Optional<Pauta> possivelPauta = service.obterPorId(id);

        if (possivelPauta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pauta pauta = possivelPauta.get();

        boolean sessaoIniciada = service.iniciarSessao(pauta, sessao.getMinutos());

        if(!sessaoIniciada){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não foi possível iniciar a sessão para esta Pauta, sua sessão já foi iniciada ou encerrada.");
        }

        log.info("Iniciada sessão para a pauta \"{}\".", pauta.getDescricao());

        return ResponseEntity.ok().build();
    }
}
