package com.zup.br.proposta.model;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "aviso", url = "http://localhost:8888")
public interface AvisoClient {

	@PostMapping("/api/cartoes/{id}/avisos")
	AvisoResponse incluirAviso(@RequestParam String id, @RequestBody AvisoRequest avisoRequest);
	
	class AvisoRequest {
		
		private String destino;
		private LocalDate validoAte;
		
		public AvisoRequest(Aviso aviso) {
			this.destino = aviso.getDestino();
			this.validoAte = aviso.getDataRetorno();
		}

		public String getDestino() {
			return destino;
		}

		public void setDestino(String destino) {
			this.destino = destino;
		}

		public LocalDate getValidoAte() {
			return validoAte;
		}

		public void setValidoAte(LocalDate validoAte) {
			this.validoAte = validoAte;
		}
		
		
	}
	
	class AvisoResponse {
		
		private String resultado;
		
		public String getResultado() {
			return resultado;
		}
	}
}
