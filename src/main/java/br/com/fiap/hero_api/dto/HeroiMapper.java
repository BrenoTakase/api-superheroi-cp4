package br.com.fiap.hero_api.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.fiap.hero_api.model.Heroi;

@Component
public class HeroiMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Heroi toModel(HeroiCreateRequest dto) {
        return modelMapper.map(dto, Heroi.class);
    }

    public HeroiResponse toDto(Heroi entity) {
        return modelMapper.map(entity, HeroiResponse.class);
    }

    public Heroi toModel(Long id, HeroiUpdateRequest dto) {
        Heroi heroi = modelMapper.map(dto, Heroi.class);
        heroi.setId(id);
        return heroi;
    }
}
