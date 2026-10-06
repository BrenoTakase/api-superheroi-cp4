package br.com.fiap.hero_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.hero_api.model.Heroi;

@Repository
public interface HeroiRepository extends JpaRepository<Heroi, Long> {

    // Consulta derivada do nome do método (Spring Data JPA)
    List<Heroi> findByUniversoIgnoreCase(String universo);

}
