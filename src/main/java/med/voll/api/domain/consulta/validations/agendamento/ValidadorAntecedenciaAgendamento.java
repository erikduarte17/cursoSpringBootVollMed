package med.voll.api.domain.consulta.validations.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorAntecedenciaAgendamento implements ValidadorAgendamentoConsulta {

    public void validar (DadosAgendamentoConsulta dados) {
       var dataEHora = dados.dataEHora();
       if(dataEHora.minusMinutes(30).isAfter(LocalDateTime.now()))
           throw new ValidationException("Horário marcado sem os 30 minutos de antecedência!");
    }
}
