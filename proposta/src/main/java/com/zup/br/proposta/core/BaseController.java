package com.zup.br.proposta.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BaseController {
    //coração do sistema para ensagens padrão de erros

    public static class RetornoPadrao implements Serializable {

        private Boolean status = true;
        private String mensagem;
        private List<?> resultados = Collections.emptyList();

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public List<?> getResultados() {
            return resultados;
        }

        public void setResultados(List<?> resultados) {
            this.resultados = resultados;
        }

    }
    public ResponseEntity<RetornoPadrao> sucesso(String mensagem, List<?>... resultados){
        RetornoPadrao retornoPadrao = new RetornoPadrao();
        retornoPadrao.setMensagem(mensagem);

        //se existe resultados, o resultado é definido no retorno padrão
        if(resultados != null){
            retornoPadrao.setResultados(Arrays.asList(resultados));
        }
        return ResponseEntity.status(HttpStatus.OK).body(retornoPadrao);
    }
}