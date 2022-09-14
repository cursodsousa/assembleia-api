package io.github.cursodsousa.assembleiaapi.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "pauta")
@Data
public class Pauta {

    public static final Integer MINUTOS_TEMPO_PADRAO_SESSAO = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String descricao;
    @Column
    private LocalDateTime abertura;
    @Column
    private LocalDateTime fechamento;

    @Deprecated
    public Pauta() {}

    public Pauta(@NotBlank String descricao) {
        this.descricao = descricao;
    }

    public boolean teveSessao(){
        return isSessaoFoiIniciada() && isSessaoEncerrada();
    }

    public boolean isSessaoFoiIniciada() {
        return abertura != null;
    }

    public boolean isSessaoEncerrada() {
        return this.fechamento != null && LocalDateTime.now().isAfter(fechamento);
    }

    public boolean isPossivelIniciarSessao(){
        return !isSessaoFoiIniciada() && !isSessaoEncerrada();
    }

    public boolean isPossivelVotar() {
        return isSessaoFoiIniciada() && !isSessaoEncerrada();
    }
}
