package br.com.maratonaenem.service.mapper;

import br.com.maratonaenem.domain.*;
import br.com.maratonaenem.service.dto.AlunoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Aluno and its DTO AlunoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlunoMapper extends EntityMapper<AlunoDTO, Aluno> {


}
