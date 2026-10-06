package br.com.fiap.hero_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "herois")
public class Heroi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codinome", length = 100, nullable = false)
    private String codinome;

    @Column(name = "nome_civil", length = 100)
    private String nomeCivil;

    @Column(name = "poder", length = 150, nullable = false)
    private String poder;

    @Column(name = "universo", length = 50, nullable = false)
    private String universo;

    @Column(name = "nivel_poder", nullable = false)
    private Integer nivelPoder;

    public Heroi(Long id, String codinome, String nomeCivil, String poder, String universo, Integer nivelPoder) {
        this.id = id;
        this.codinome = codinome;
        this.nomeCivil = nomeCivil;
        this.poder = poder;
        this.universo = universo;
        this.nivelPoder = nivelPoder;
    }

    public Heroi() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodinome() {
        return codinome;
    }

    public void setCodinome(String codinome) {
        this.codinome = codinome;
    }

    public String getNomeCivil() {
        return nomeCivil;
    }

    public void setNomeCivil(String nomeCivil) {
        this.nomeCivil = nomeCivil;
    }

    public String getPoder() {
        return poder;
    }

    public void setPoder(String poder) {
        this.poder = poder;
    }

    public String getUniverso() {
        return universo;
    }

    public void setUniverso(String universo) {
        this.universo = universo;
    }

    public Integer getNivelPoder() {
        return nivelPoder;
    }

    public void setNivelPoder(Integer nivelPoder) {
        this.nivelPoder = nivelPoder;
    }

}
