package io.github.cursodsousa.assembleiaapi.infra.repository;

import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
