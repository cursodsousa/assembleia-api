package io.github.cursodsousa.assembleiaapi.infra.repository;

import io.github.cursodsousa.assembleiaapi.domain.model.Associado;
import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import io.github.cursodsousa.assembleiaapi.domain.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByAssociadoAndPauta(Associado associado, Pauta pauta);

    Set<Voto> findByPauta(Pauta pauta);
}
