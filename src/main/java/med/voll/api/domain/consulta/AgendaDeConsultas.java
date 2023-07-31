package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.validations.agendamento.ValidadorAgendamentoConsulta;
import med.voll.api.domain.consulta.validations.cancelamento.ValidadorCancelamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoConsulta> validadoresAgendamento;
    @Autowired
    private List<ValidadorCancelamentoConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar (DadosAgendamentoConsulta dados) {

        validadoresAgendamento.forEach(val -> val.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.dataEHora(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar (DadosCancelamentoConsulta dados) {
        validadoresCancelamento.forEach(val -> val.validar(dados));

        if (dados.id() != null) {
            var consulta = consultaRepository.getReferenceById(dados.id());
            consultaRepository.delete(consulta);
        }
        throw new ValidationException("ID da Consulta é Inválido!!");
    }


    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null)
            return medicoRepository.getReferenceById(dados.idMedico());
        if(dados.especialidade() == null)
            throw new ValidationException("Especialidade é obrigatória na ausência da seleção do Médico");
        return medicoRepository.escolherMedicoAleatorioDisponivel(dados.especialidade(), dados.dataEHora());
    }
}
