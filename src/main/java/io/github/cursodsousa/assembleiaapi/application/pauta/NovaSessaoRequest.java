package io.github.cursodsousa.assembleiaapi.application.pauta;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@ApiModel("NovaSessao")
public class NovaSessaoRequest {
    @Positive(message = "deve ser um valor positivo")
    @Min(value = 1, message = "deve ser maior que zero")
    private Integer minutos;
}
