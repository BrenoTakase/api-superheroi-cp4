package br.com.fiap.hero_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.hero_api.model.Heroi;
import br.com.fiap.hero_api.repository.HeroiRepository;

@Service
public class HeroiService {
    @Autowired
    private HeroiRepository repository;

    public Heroi createOrUpdate(Heroi heroi) {
        return repository.save(heroi);
    }

    public Optional<Heroi> findById(Long id) {
        return repository.findById(id);
    }

    public List<Heroi> findAll() {
        return repository.findAll();
    }

    public List<Heroi> findByUniverso(String universo) {
        return repository.findByUniversoIgnoreCase(universo);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
