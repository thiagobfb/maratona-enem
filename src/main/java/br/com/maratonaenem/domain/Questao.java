package br.com.maratonaenem.domain;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * Generated by JHipster IDE plugin
 */
@ApiModel(description = "Generated by JHipster IDE plugin")
@Document(collection = "questao")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "questao")
public class Questao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("descricao")
    private String descricao;
    
    @Field("disciplina")
    private Disciplina disciplina;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Questao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
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
        Questao questao = (Questao) o;
        if (questao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Questao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", disciplia='" + getDisciplina() + "'" +
            "}";
    }
}
