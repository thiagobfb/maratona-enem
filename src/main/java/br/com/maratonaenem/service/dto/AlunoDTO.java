package br.com.maratonaenem.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Aluno entity.
 */
public class AlunoDTO implements Serializable {

	private static final long serialVersionUID = -8791860715255440386L;

	private String id;

    private String nome;

    private String email;

    private Integer pontuacao;
    
    private EscolaDTO escola;
    
    private UserDTO user;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public EscolaDTO getEscola() {
		return escola;
	}

	public void setEscola(EscolaDTO escola) {
		this.escola = escola;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlunoDTO alunoDTO = (AlunoDTO) o;
        if(alunoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alunoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlunoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", pontuacao=" + getPontuacao() +
            "}";
    }
}
