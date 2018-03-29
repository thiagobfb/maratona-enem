package br.com.maratonaenem.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Questao entity.
 */
public class QuestaoDTO implements Serializable {

	private static final long serialVersionUID = 2341975180018998456L;

	private String id;

    private String descricao;
    
    private DisciplinaDTO disciplina;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DisciplinaDTO getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaDTO disciplina) {
		this.disciplina = disciplina;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestaoDTO questaoDTO = (QuestaoDTO) o;
        if(questaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestaoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
