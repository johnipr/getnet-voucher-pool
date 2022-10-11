package com.getnet.app.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "offers")
public class Offer {

	@Id
	private String id;

	private String name;
	private Double percentDiscount;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate expirationDate;
	
	public Offer(String name,  Double percentDiscount, LocalDate expirationDate){
		this.name = name;
		this.percentDiscount = percentDiscount;
		this.expirationDate = expirationDate;
	}

}
