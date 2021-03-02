package com.zup.br.proposta.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraDto {

	@NotNull
	@Email
	private String email;
	@NotBlank
	private String id;
	
	public CarteiraDto(@NotNull @Email String email, @NotBlank String id) {
		super();
		this.email = email;
		this.id = id;
	}

	public CarteiraDto() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
