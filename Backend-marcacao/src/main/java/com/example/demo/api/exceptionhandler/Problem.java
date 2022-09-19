package com.example.demo.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public class Problem {

	private LocalDateTime dataHora;
	private String mensagem;	
		
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
	
}
