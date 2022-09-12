package com.noname.uol.entidades;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Images {	
	
	private String url;

}
