package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mock;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> jacksonTesterAgendamento;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> jacksonTesterDetalhamento;
    @MockBean
    private AgendaDeConsultas agenda;

    @Test
    @DisplayName("Deveria retornar status Http400 quando passar as informações inválidas")
    @WithMockUser
    void agendarCenario1 () throws Exception {
        var response = mock.perform(post("/consultas"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar status Http200 quando passar informações válidas")
    @WithMockUser
    void agendarCenario2 () throws Exception {

        var dadosDetalhamento = new DadosDetalhamentoConsulta(
                null, 2L, 5L, LocalDateTime.now().plusHours(1));
        when(agenda.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mock.perform(
                        post("/consultas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jacksonTesterAgendamento.write(
                                new DadosAgendamentoConsulta(
                                        2L, 5L, LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA))
                            .getJson()))
                    .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = jacksonTesterDetalhamento.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}