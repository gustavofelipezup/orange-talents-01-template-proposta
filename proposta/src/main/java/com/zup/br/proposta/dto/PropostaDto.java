package com.zup.br.proposta.dto;

import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.zup.br.proposta.model.Endereco;

public class PropostaDto {

	@NotBlank
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
	
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	
}
