package com.zup.br.proposta.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "bloqueio", url = "${contas.host}")
public interface BloqueioClient {

	@PostMapping("/api/cartoes/{id}/bloqueios")
	BloqueioResponse bloquear(@RequestParam String id, @RequestBody BloqueioRequest bloqueioRequest);
	
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