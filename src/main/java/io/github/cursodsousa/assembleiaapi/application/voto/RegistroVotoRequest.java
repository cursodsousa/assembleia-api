package io.github.cursodsousa.assembleiaapi.application.voto;

import io.github.cursodsousa.assembleiaapi.domain.model.enums.OpcaoVoto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("RegistroVoto")
public class RegistroVotoRequest {

    @CPF(message = "Não é um cpf válido")
    @NotBlank(message = "Campo Obrigatório")
    private String cpf;

    @NotNull(message = "Campo Obrigatório")
    private OpcaoVoto opcao;
}
