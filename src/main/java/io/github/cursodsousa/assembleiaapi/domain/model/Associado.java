package io.github.cursodsousa.assembleiaapi.domain.model;


import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "associado")
@Data
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cpf;

    @Deprecated
    public Associado() {}

    public Associado(@CPF @NotBlank String cpf) {
        this.cpf = cpf;
    }
}
