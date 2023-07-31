package med.voll.api.domain.consulta.validations.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDeMedicoInativo implements ValidadorAgendamentoConsulta {

    @Autowired
    private MedicoRepository medicoRepository;
    public void validar(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() == null)
            return;

        if(!medicoRepository.findAtivoById(dados.idMedico()))
            throw new ValidationException("Medico invalido para agendamentos!");
    }
}
