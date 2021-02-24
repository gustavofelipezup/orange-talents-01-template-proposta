package com.zup.br.proposta.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.zup.br.proposta.core.Status;
import com.zup.br.proposta.core.validator.CpfCnpj;
import com.zup.br.proposta.model.AcompanhamentoProposta.NovoCartaoResponse;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@CpfCnpj
	@Column(unique = true)
	private String documento;
	@NotBlank
	private String nome;
	@NotNull
	@Embedded
	private Endereco endereco;
	@NotBlank
	@Email
	private String email;
	@PositiveOrZero
	@NotNull
	private BigDecimal salario;
	@Enumerated(EnumType.STRING)
	private Status status;
	private Boolean processado = false;
	@OneToOne(mappedBy = "proposta", cascade = CascadeType.MERGE)
	private Cartao cartao;
	
	public Proposta() {
		super();
	}

	public Proposta(String documento, String nome, Endereco endereco, String email, BigDecimal salario) {
		super();
		this.documento = documento;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
		this.salario = salario;
	}
	
	public void associaCartao(AcompanhamentoProposta.ConsultaCartaoResponse novoCartaoResponse) {
		this.cartao = new Cartao(novoCartaoResponse.getId(), this, novoCartaoResponse.getEmitidoEm());
	}

	public Long getId() {
		return id;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public String getEmail() {
		return email;
	}

	public BigDecimal getSalario() {
		return salario;
	}
	
	public Boolean getProcessado() {
		return processado;
	}
	
	public void setProcessado() {
		this.processado = true;
	}

	public void atualizaStatus(String solicitacao) {
		
		this.status = Status.resultadoPara(solicitacao);
		
	}
}
