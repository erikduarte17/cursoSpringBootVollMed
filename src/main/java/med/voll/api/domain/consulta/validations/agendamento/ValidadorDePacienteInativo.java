package med.voll.api.domain.consulta.validations.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDePacienteInativo implements ValidadorAgendamentoConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;
    public void validar(DadosAgendamentoConsulta dados) {
        if(!pacienteRepository.findAtivoById(dados.idMedico()))
            throw new ValidationException("Paciente invalido para agendamentos!");
    }
}
