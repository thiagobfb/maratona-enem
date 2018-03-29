package br.com.maratonaenem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.maratonaenem.domain.enumeration.TipoEscola;

/**
 * A Escola.
 */
@Document(collection = "escola")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "escola")
public class Escola implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nome")
    private String nome;

    @Field("tipo_escola")
    private TipoEscola tipoEscola;
    
    @Field("alunos")
    private List<Aluno> alunos = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Escola nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoEscola getTipoEscola() {
        return tipoEscola;
    }

    public Escola tipoEscola(TipoEscola tipoEscola) {
        this.tipoEscola = tipoEscola;
        return this;
    }

    public void setTipoEscola(TipoEscola tipoEscola) {
        this.tipoEscola = tipoEscola;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Escola escola = (Escola) o;
        if (escola.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escola.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Escola{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipoEscola='" + getTipoEscola() + "'" +
            "}";
    }
}
