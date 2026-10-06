package br.com.fiap.hero_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class HeroiUpdateRequest {
    @NotBlank(message = "O codinome do herói é obrigatório")
    @Size(min = 3, max = 100, message = "Codinome deve ter entre 3 e 100 caracteres")
    private String codinome;

    @Size(max = 100, message = "Nome civil deve ter no máximo 100 caracteres")
    private String nomeCivil;

    @NotBlank(message = "O poder do herói é obrigatório")
    @Size(max = 150, message = "Poder deve ter no máximo 150 caracteres")
    private String poder;

    @NotBlank(message = "O universo do herói é obrigatório")
    @Size(max = 50, message = "Universo deve ter no máximo 50 caracteres")
    private String universo;

    @NotNull(message = "O nível de poder é obrigatório")
    @Min(value = 1, message = "O nível de poder deve ser no mínimo 1")
    @Max(value = 100, message = "O nível de poder deve ser no máximo 100")
    private Integer nivelPoder;

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
