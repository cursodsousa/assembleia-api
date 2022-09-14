package io.github.cursodsousa.assembleiaapi.application.pauta;

import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("Pauta")
public class NovaPautaRequest {

    @NotBlank(message = "campo obrigatório")
    private String descricao;

    public Pauta toModel() {
        return new Pauta(descricao);
    }
}
