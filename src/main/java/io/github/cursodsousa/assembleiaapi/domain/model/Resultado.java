package io.github.cursodsousa.assembleiaapi.domain.model;

import io.github.cursodsousa.assembleiaapi.domain.model.enums.OpcaoVoto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("Resultado")
@AllArgsConstructor
@Builder
public class Resultado {

    private String pauta;
    private LocalDateTime abertura;
    private LocalDateTime encerramento;
    private Long totalVotos;
    private Long quantidadeSim;
    private Long quantiadeNao;
    private OpcaoVoto opcaoGanhadora;

}
