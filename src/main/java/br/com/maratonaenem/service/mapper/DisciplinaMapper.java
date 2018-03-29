package br.com.maratonaenem.service.mapper;

import br.com.maratonaenem.domain.*;
import br.com.maratonaenem.service.dto.DisciplinaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Disciplina and its DTO DisciplinaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DisciplinaMapper extends EntityMapper<DisciplinaDTO, Disciplina> {


}
