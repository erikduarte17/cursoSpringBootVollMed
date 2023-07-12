package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import med.voll.api.paciente.dto.DadosAtualizacaoPaciente;
import med.voll.api.paciente.dto.DadosCadastroPaciente;
import med.voll.api.paciente.dto.DadosListagemPacientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar (@RequestBody @Valid DadosCadastroPaciente dados) {
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPacientes> listar (
            @PageableDefault(sort = "nome", size = 10) Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemPacientes::new);
    }

    @PutMapping
    @Transactional
    public void atualizar (@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar (@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.desativar();
    }
}
