package br.com.maratonaenem.service.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.maratonaenem.domain.enumeration.TipoDisciplina;

/**
 * A DTO for the Disciplina entity.
 */
public class DisciplinaDTO implements Serializable {

	private static final long serialVersionUID = 7355619991949417726L;

	private String id;

    private String nome;

    private TipoDisciplina tipoDisciplina;
    
    private List<QuestaoDTO> questoes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoDisciplina getTipoDisciplina() {
        return tipoDisciplina;
    }

    public void setTipoDisciplina(TipoDisciplina tipoDisciplina) {
        this.tipoDisciplina = tipoDisciplina;
    }

    public List<QuestaoDTO> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<QuestaoDTO> questoes) {
		this.questoes = questoes;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DisciplinaDTO disciplinaDTO = (DisciplinaDTO) o;
        if(disciplinaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disciplinaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DisciplinaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipoDisciplina='" + getTipoDisciplina() + "'" +
            "}";
    }
}
