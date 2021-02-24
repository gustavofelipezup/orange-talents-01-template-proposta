package com.zup.br.proposta.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "bloqueio", url = "http://localhost:8888")
public interface BloqueioClient {

	@PostMapping("/api/cartoes/{id}/bloqueios")
	BloqueioResponse bloquear(@RequestBody BloqueioRequest bloqueioRequest);
	
	class BloqueioRequest {
		
		private String sistemaResponsavel;

		public BloqueioRequest(String sistemaResponsavel) {
			this.sistemaResponsavel = sistemaResponsavel;
		}
		
		public String getSistemaResponsavel() {
			return sistemaResponsavel;
		}
		
	}
	
	class BloqueioResponse {
		
		private String resultado;

		public String getResultado() {
			return resultado;
		}
		
		
	}
}