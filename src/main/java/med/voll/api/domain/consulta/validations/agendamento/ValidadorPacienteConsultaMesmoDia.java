package med.voll.api.domain.consulta.validations.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteConsultaMesmoDia implements ValidadorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar (DadosAgendamentoConsulta dados) {
        if(!repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), dados.dataEHora().withHour(7), dados.dataEHora().withHour(18)))
            throw new ValidationException("Paciente ja possui outra consulta no mesmo dia!");

    }
}
