package com.zup.br.proposta.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonProperty;

@FeignClient(name = "acompanhamentoProposta", url = "http://localhost:8888")
public interface AcompanhamentoProposta {
	
	@PostMapping("/api/cartoes")
	NovoCartaoResponse consulta(@RequestBody NovoCartaoRequest novoCartaoRequest);
	
	class NovoCartaoRequest {
		
		private String documento;
		private String nome;
		private Long idProposta;
		
		public NovoCartaoRequest(Proposta proposta) {
			this.documento = proposta.getDocumento();
			this.nome = proposta.getNome();
			this.idProposta = proposta.getId();
		}
		
		public String getDocumento() {
			return documento;
		}

		public String getNome() {
			return nome;
		}

		public Long getIdProposta() {
			return idProposta;
		}
	}
	
	class NovoCartaoResponse {
		
		@JsonProperty
	    private String id;
	    @JsonProperty
	    private String titular;
	    @JsonProperty
	    private LocalDateTime emitidoEm;
	    @JsonProperty
	    private Long idProposta;
	    @JsonProperty
	    private BigDecimal limite;
	    
		public String getId() {
			return id;
		}
		public String getTitular() {
			return titular;
		}
		public LocalDateTime getEmitidoEm() {
			return emitidoEm;
		}
		public Long getIdProposta() {
			return idProposta;
		}
		public BigDecimal getLimite() {
			return limite;
		}
	    
	    
	}
	
	@GetMapping("api/cartoes/{id}")
	ConsultaCartaoResponse consulta(@RequestParam("idProposta") Long idProposta);
	
	class ConsultaCartaoRequest {
		
		private Long idProposta;
		
		public ConsultaCartaoRequest(Proposta proposta) {
			this.idProposta = proposta.getId();
		}
		
		public Long getIdProposta() {
			return idProposta;
		}
	}
	
	class ConsultaCartaoResponse {
		
		private String id;
		private LocalDateTime emitidoEm;
		private String titular;
		private BigDecimal limite;
		private Long idProposta;
		private Proposta proposta;

		public ConsultaCartaoResponse(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, Long idProposta) {
			this.id = id;
			this.emitidoEm = emitidoEm;
			this.titular = titular;
			this.limite = limite;
			this.idProposta = idProposta;
		}
		public String getId() {
			return id;
		}
		public LocalDateTime getEmitidoEm() {
			return emitidoEm;
		}
		public String getTitular() {
			return titular;
		}

		public Long getIdProposta() {
			return idProposta;
		}
		
		public Proposta getProposta() {
			return proposta;
		}
		
		public BigDecimal getLimite() {
			return limite;
		}
	}
	

}
