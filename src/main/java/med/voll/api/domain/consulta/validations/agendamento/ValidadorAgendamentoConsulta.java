package med.voll.api.domain.consulta.validations.agendamento;

import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public interface ValidadorAgendamentoConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
