package com.zup.br.proposta.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "carteira", url = "${contas.host}")
public interface CarteiraClient {

	@PostMapping("/api/cartoes/{id}/carteiras")
	CarteiraResponse adicionarMetodo(@RequestParam String id, @RequestBody CarteiraRequest carteiraRequest);
	
	class CarteiraRequest {
		
		private String email;
		private String carteira;
		
		public CarteiraRequest(Carteira carteira) {
			this.email = carteira.getEmail();
			this.carteira = carteira.getNome();
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCarteira() {
			return carteira;
		}

		public void setCarteira(String carteira) {
			this.carteira = carteira;
		}
	}
	
	class CarteiraResponse {
		
		private String resultado;
		private String id;
		
		public String getResultado() {
			return resultado;
		}
		
		public String getId() {
			return id;
		}
	}
}
