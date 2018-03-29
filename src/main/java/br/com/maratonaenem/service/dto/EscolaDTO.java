package br.com.maratonaenem.service.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.maratonaenem.domain.enumeration.TipoEscola;

/**
 * A DTO for the Escola entity.
 */
public class EscolaDTO implements Serializable {

	private static final long serialVersionUID = 1408336744862612282L;

	private String id;

    private String nome;

    private TipoEscola tipoEscola;
    
    private List<AlunoDTO> alunos = new ArrayList<>();

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

    public TipoEscola getTipoEscola() {
        return tipoEscola;
    }

    public void setTipoEscola(TipoEscola tipoEscola) {
        this.tipoEscola = tipoEscola;
    }

    public List<AlunoDTO> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoDTO> alunos) {
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

        EscolaDTO escolaDTO = (EscolaDTO) o;
        if(escolaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escolaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EscolaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipoEscola='" + getTipoEscola() + "'" +
            "}";
    }
}
