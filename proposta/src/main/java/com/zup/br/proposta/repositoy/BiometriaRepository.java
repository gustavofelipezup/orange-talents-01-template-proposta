package com.zup.br.proposta.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zup.br.proposta.model.Biometria;

@Repository
public interface BiometriaRepository extends JpaRepository<Biometria, Long>{

}
