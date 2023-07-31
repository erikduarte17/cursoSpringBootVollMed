package med.voll.api.domain.consulta.validations.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConsultaHorario implements ValidadorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar (DadosAgendamentoConsulta dados) {
        if(!consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.dataEHora()))
            throw new ValidationException("Medico ja possui consulta neste horário!");
    }
}
