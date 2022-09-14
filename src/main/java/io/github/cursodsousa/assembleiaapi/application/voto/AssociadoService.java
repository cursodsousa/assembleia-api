package io.github.cursodsousa.assembleiaapi.application.voto;

import io.github.cursodsousa.assembleiaapi.domain.model.Associado;
import io.github.cursodsousa.assembleiaapi.infra.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssociadoService {
    private final AssociadoRepository repository;

    public Optional<Associado> obterPorCpf(String cpf){
        return repository.findByCpf(cpf);
    }

    @Transactional
    public Associado recuperarOuCadastrar(String cpf) {
        return obterPorCpf(cpf).orElseGet(() -> repository.save(new Associado(cpf)));
    }
}
