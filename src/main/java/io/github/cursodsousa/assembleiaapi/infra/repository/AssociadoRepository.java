package io.github.cursodsousa.assembleiaapi.infra.repository;

import io.github.cursodsousa.assembleiaapi.domain.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {
    Optional<Associado> findByCpf(String cpf);
}
