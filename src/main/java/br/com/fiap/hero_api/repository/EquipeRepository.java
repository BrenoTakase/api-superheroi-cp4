package br.com.fiap.hero_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.hero_api.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}
