package com.example.demo.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_NAO_LEGIVEL("/mensagem-nao-legivel", "Mensagem não legível"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
	ERRO_DE_NEGOCIO("/erro-de-negocio", "Violação de regra de negócio"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada.");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://pacientes.com.br" + path;
		this.title = title;
	}
	
}
