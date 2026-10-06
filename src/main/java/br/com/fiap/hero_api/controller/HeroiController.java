package br.com.fiap.hero_api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.hero_api.dto.HeroiCreateRequest;
import br.com.fiap.hero_api.dto.HeroiMapper;
import br.com.fiap.hero_api.dto.HeroiResponse;
import br.com.fiap.hero_api.dto.HeroiUpdateRequest;
import br.com.fiap.hero_api.model.Heroi;
import br.com.fiap.hero_api.service.HeroiService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/${api.version}/herois")
public class HeroiController {

    @Autowired
    private HeroiService service;

    @Autowired
    private HeroiMapper heroiMapper;

    @PostMapping
    public ResponseEntity<HeroiResponse> create(@Valid @RequestBody HeroiCreateRequest dtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(heroiMapper.toDto(
                service.createOrUpdate(
                    heroiMapper.toModel(dtoRequest)
                )
            ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroiResponse> findById(@PathVariable Long id) {
        return service
                .findById(id)
                .map(heroi -> heroiMapper.toDto(heroi))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<HeroiResponse>> findAll(
                                @RequestParam(required = false) String universo) {

        List<Heroi> herois = (universo == null || universo.isBlank())
                ? service.findAll()
                : service.findByUniverso(universo);

        return ResponseEntity.ok(
                herois.stream()
                .map(heroi -> heroiMapper.toDto(heroi))
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeroiResponse> update(@PathVariable Long id,
                                @Valid @RequestBody HeroiUpdateRequest dtoRequest) {

        if (service.findById(id).isPresent()) {
            Heroi heroi = heroiMapper.toModel(id, dtoRequest);
            return ResponseEntity.ok(heroiMapper.toDto(
                            service.createOrUpdate(heroi)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
