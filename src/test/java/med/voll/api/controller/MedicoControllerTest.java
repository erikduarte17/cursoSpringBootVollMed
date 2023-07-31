package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.dto.DadosCadastroMedico;
import med.voll.api.domain.medico.dto.DadosDetalhamentoMedico;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mock;
    @Autowired
    private JacksonTester<DadosCadastroMedico> jacksonTesterCadastro;
    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> jacksonTesterDetalhamento;
    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Cadastrar médico no Banco de Dados passando informações inválidas")
    @WithMockUser
    void cadastrarCenario1 () throws Exception {
        var response = mock.perform(post("/medicos"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Cadastrar médico no Banco de Dados passando informações válidas")
    @WithMockUser
    void cadastrarCenario2 () throws Exception {

        var dadosCadastro = new DadosCadastroMedico(
                "Medico", "medico@voll.med", "51654565454",
                "132456", Especialidade.CARDIOLOGIA, dadosEndereco());

        var dadosDetalhamento = new DadosDetalhamentoMedico(null, dadosCadastro.nome(), dadosCadastro.email(), dadosCadastro.telefone(),
                dadosCadastro.crm(), dadosCadastro.especialidade(), new Endereco(dadosCadastro.endereco()));

        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

        var response = mock.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonTesterCadastro.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var jsonEsperado = jacksonTesterDetalhamento.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco("rua xpto", "bairro", "Brasilia", "DF", "12345678", null, null);
    }

}