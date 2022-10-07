package com.example.demo.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

	@ApiModelProperty(example = "0", value = "Número da página")
	private int page;
	
	@ApiModelProperty(example = "10", value = "Número de elementos por página")
	private int size;
	
	@ApiModelProperty(example = "nome.asc", value = "Número da propriedade para ordenação")
	private List<String> sort;
	
}
