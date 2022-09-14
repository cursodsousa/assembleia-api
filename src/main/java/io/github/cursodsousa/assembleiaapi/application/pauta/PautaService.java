package io.github.cursodsousa.assembleiaapi.application.pauta;

import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import io.github.cursodsousa.assembleiaapi.infra.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository repository;

    @Transactional
    public Pauta criar(Pauta pauta) {
        return repository.save(pauta);
    }

    public Optional<Pauta> obterPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public boolean iniciarSessao(Pauta pauta, Integer minutos) {
        if(!pauta.isPossivelIniciarSessao()){
            return false;
        }

        if(minutos == null){
            minutos = Pauta.MINUTOS_TEMPO_PADRAO_SESSAO;
        }

        LocalDateTime agora = LocalDateTime.now();
        pauta.setAbertura(agora);

        pauta.setFechamento(agora.plusMinutes(minutos));

        repository.save(pauta);

        return true;
    }
}
