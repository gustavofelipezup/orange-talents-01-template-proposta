package com.zup.br.proposta.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zup.br.proposta.model.AcompanhamentoProposta;
import com.zup.br.proposta.model.Proposta;
import com.zup.br.proposta.repositoy.PropostaRepository;

import feign.FeignException;

@Component
public class ProcessaPropostasTask {
	
	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private AcompanhamentoProposta acompanhamentoProposta;
	
	@Scheduled(fixedDelay = 5000)
	public void processar() {
		
		List<Proposta> propostas = propostaRepository.findByProcessado(false);
		
		//para cada proposta em propostas
		for (Proposta p : propostas) {
			
			try {
			AcompanhamentoProposta.ConsultaCartaoResponse cartao = acompanhamentoProposta.consulta(p.getId());
			p.setProcessado();
			p.associaCartao(cartao);
			propostaRepository.save(p);
			} catch (FeignException e){
				System.out.println(e.getMessage());
			}
		}
	}
}
