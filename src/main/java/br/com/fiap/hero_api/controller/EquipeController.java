package br.com.fiap.hero_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.hero_api.model.Equipe;
import br.com.fiap.hero_api.repository.EquipeRepository;

@RestController
@RequestMapping("api/${api.version}/equipes")
public class EquipeController {

    @Autowired
    private EquipeRepository repository;

    @PostMapping
    public ResponseEntity<Equipe> create(@RequestBody Equipe equipe) {
        equipe.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(equipe));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> findById(@PathVariable Long id) {
        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Equipe>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> update(@PathVariable Long id,
                                @RequestBody Equipe equipe) {

        Optional<Equipe> optEquipe = repository.findById(id);

        if (optEquipe.isPresent()) {
            equipe.setId(id);
            Equipe equipeAlterada = repository.save(equipe);
            return ResponseEntity.ok(equipeAlterada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
