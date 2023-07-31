package med.voll.api.domain.consulta.validations.cancelamento;

import med.voll.api.domain.consulta.dto.DadosCancelamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public interface ValidadorCancelamentoConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
