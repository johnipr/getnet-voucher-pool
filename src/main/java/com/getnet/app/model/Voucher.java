package com.getnet.app.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vouchers")
public class Voucher {

	@Id
	private String id;
	private String code;
	private String clientName;
	@Email
	private String email;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate redeemDate;

	private Offer offer;


}
