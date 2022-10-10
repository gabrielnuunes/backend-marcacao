package com.example.demo.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@ApiModelProperty(value = "400")
	private Integer status;
	
	@ApiModelProperty(value = "2022-09-05T22:15:02.70844Z")
	private LocalDateTime timestamp;
	
	@ApiModelProperty(value = "http://com.example.demo/dados-invalidos ")
	private String type;
	
	@ApiModelProperty(value = "Dados inválidos")
	private String title;
	
	@ApiModelProperty(value = "Um ou mais campos estão inválidos. Favor realizar o preenchimento correto.")
	private String detail;
	
	@ApiModelProperty(value = "Um ou mais campos estão inválidos. Favor realizar o preenchimento correto.")	
	private String userMessage;
	
	
	
}
