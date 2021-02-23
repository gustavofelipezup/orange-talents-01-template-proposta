package com.zup.br.proposta.repositoy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zup.br.proposta.core.Status;
import com.zup.br.proposta.model.Proposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

	Optional<Proposta> findByDocumento(String documento);
	
	List<Proposta> findByStatus(Enum<Status> status);
	
	
}
