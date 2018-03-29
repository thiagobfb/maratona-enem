package br.com.maratonaenem.service.mapper;

import br.com.maratonaenem.domain.*;
import br.com.maratonaenem.service.dto.EscolaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Escola and its DTO EscolaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EscolaMapper extends EntityMapper<EscolaDTO, Escola> {


}
