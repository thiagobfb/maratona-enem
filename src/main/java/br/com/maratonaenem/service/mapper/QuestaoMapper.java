package br.com.maratonaenem.service.mapper;

import br.com.maratonaenem.domain.*;
import br.com.maratonaenem.service.dto.QuestaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Questao and its DTO QuestaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestaoMapper extends EntityMapper<QuestaoDTO, Questao> {


}
