package med.voll.api.domain.consulta.validations.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsulta {

    public void validar (DadosAgendamentoConsulta dados) {
       var domingo = dados.dataEHora().getDayOfWeek().equals(DayOfWeek.SUNDAY);
       var horario = dados.dataEHora().getHour() < 7 || dados.dataEHora().getHour() > 18;

       if(domingo || horario)
           throw new ValidationException("Consulta fora do horário de funcionamento ou em Domingo!");

    }
}
