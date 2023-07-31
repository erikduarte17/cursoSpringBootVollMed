package med.voll.api.domain.consulta.validations.cancelamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DadosCancelamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMotivoCancelamento implements ValidadorCancelamentoConsulta {

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        if(dados.motivoCancelamento() == null)
            throw new ValidationException("Campo motivo do cancelamento deve ser um dos seguinte");
    }
}
