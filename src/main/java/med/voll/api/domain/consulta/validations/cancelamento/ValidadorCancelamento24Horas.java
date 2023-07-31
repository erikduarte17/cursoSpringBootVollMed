package med.voll.api.domain.consulta.validations.cancelamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorCancelamento24Horas implements ValidadorCancelamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var horaConsulta = repository.findById(dados.id()).orElseThrow(ValidationException::new).getData();
        if(horaConsulta.isBefore(LocalDateTime.now().minusHours(24)))
            throw new ValidationException("Cancelamento somente com 24 horas de antecedência");
    }
}
