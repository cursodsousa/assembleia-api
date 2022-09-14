package io.github.cursodsousa.assembleiaapi.application.voto;

import io.github.cursodsousa.assembleiaapi.domain.exception.StatusEntidadeInvalidoException;
import io.github.cursodsousa.assembleiaapi.domain.exception.RegistroDuplicadoException;
import io.github.cursodsousa.assembleiaapi.domain.model.Pauta;
import io.github.cursodsousa.assembleiaapi.domain.model.Resultado;
import io.github.cursodsousa.assembleiaapi.domain.model.Voto;
import io.github.cursodsousa.assembleiaapi.domain.model.enums.OpcaoVoto;
import io.github.cursodsousa.assembleiaapi.infra.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository repository;
    private final AssociadoService associadoService;

    @Transactional
    public Voto registrarVoto(Pauta pauta, String cpf, OpcaoVoto opcao) {
        if(!pauta.isPossivelVotar()){
            throw new StatusEntidadeInvalidoException("Pauta não está em sessão.");
        }

        var cooperado = associadoService.recuperarOuCadastrar(cpf);

        boolean votoRegistradoAnteriormente = repository.existsByAssociadoAndPauta(cooperado, pauta);
        if(votoRegistradoAnteriormente){
            throw new RegistroDuplicadoException("Voto já registrado anteriomente.");
        }

        var voto = new Voto(cooperado, pauta, opcao);
        return repository.save(voto);
    }

    public Resultado obterResultado(Pauta pauta){
        if(!pauta.teveSessao()){
            throw new StatusEntidadeInvalidoException("Não foi possível obter o resultado. Sessão não ocorreu para esta pauta");
        }

        Set<Voto> votosDaPauta = obterVotosPorPauta(pauta);

        Long quantidadeSim = votosDaPauta.stream().filter(v -> v.getOpcaoVoto().equals(OpcaoVoto.SIM)).collect(Collectors.counting());
        Long quantidadeNao = votosDaPauta.stream().filter(v -> v.getOpcaoVoto().equals(OpcaoVoto.NAO)).collect(Collectors.counting());

        OpcaoVoto opcaoGanhadora = obterPosicaoGanhadora(quantidadeSim, quantidadeNao);

        return Resultado
                .builder()
                .pauta(pauta.getDescricao())
                .abertura(pauta.getAbertura())
                .encerramento(pauta.getFechamento())
                .quantidadeSim(quantidadeSim)
                .quantiadeNao(quantidadeNao)
                .totalVotos(Long.valueOf(votosDaPauta.size()))
                .opcaoGanhadora(opcaoGanhadora)
                .build();
    }

    private OpcaoVoto obterPosicaoGanhadora(Long quantidadeSim, Long quantidadeNao) {
        if(quantidadeNao == quantidadeSim){
            return null;
        }

        return quantidadeSim > quantidadeNao ? OpcaoVoto.SIM : OpcaoVoto.NAO;
    }

    public Set<Voto> obterVotosPorPauta(Pauta pauta){
        return repository.findByPauta(pauta);
    }
}
