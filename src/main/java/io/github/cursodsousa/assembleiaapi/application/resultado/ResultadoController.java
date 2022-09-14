package io.github.cursodsousa.assembleiaapi.application.resultado;

import io.github.cursodsousa.assembleiaapi.application.pauta.PautaService;
import io.github.cursodsousa.assembleiaapi.application.voto.VotoService;
import io.github.cursodsousa.assembleiaapi.domain.exception.StatusEntidadeInvalidoException;
import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import io.github.cursodsousa.assembleiaapi.domain.model.Resultado;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Statement;
import java.util.Optional;

@RestController
@RequestMapping("/v1/pautas/{id}/resultados")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Resultados")
public class ResultadoController {

    private final PautaService pautaService;
    private final VotoService votoService;

    @GetMapping
    @Operation(summary = "Obter resultado",  description = "Dada uma pauta, retorna seu resultado")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada ou Resultado indisponível"),
            @ApiResponse(responseCode = "200", description = "Resultado gerado com sucesso")
    })
    public ResponseEntity<Resultado> obterResultado(@PathVariable @ApiParam("id da pauta") Long id){
        Optional<Pauta> pauta = pautaService.obterPorId(id);

        if(pauta.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        try {
            var resultado = votoService.obterResultado(pauta.get());
            return ResponseEntity.ok(resultado);
        }catch (StatusEntidadeInvalidoException e){
            return ResponseEntity.notFound().build();
        }
    }
}
