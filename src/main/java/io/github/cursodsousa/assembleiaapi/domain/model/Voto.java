package io.github.cursodsousa.assembleiaapi.domain.model;

import io.github.cursodsousa.assembleiaapi.domain.model.enums.OpcaoVoto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "voto")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_associado")
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @Enumerated(EnumType.STRING)
    private OpcaoVoto opcaoVoto;

    @Deprecated
    public Voto() {
    }

    public Voto(Associado associado, Pauta pauta, OpcaoVoto opcaoVoto) {
        this.associado = associado;
        this.pauta = pauta;
        this.opcaoVoto = opcaoVoto;
    }
}
