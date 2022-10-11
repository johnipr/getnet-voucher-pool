package com.getnet.app.dto;

import java.time.LocalDate;

import com.getnet.app.model.Voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VoucherDtoResponse {
	
	private String code;
	private String clientName;
	
//	@JsonFormat(pattern = "dd/MM/yyyy")
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate redeemDate;
	
	private Double percentDiscount;
	private String offerName;
	
	public static VoucherDtoResponse convertoModelToDto(Voucher voucher) {
		return new VoucherDtoResponse(voucher.getCode(), 
									  voucher.getClientName(), 
									  voucher.getRedeemDate(), 
									  voucher.getOffer().getPercentDiscount(),
									  voucher.getOffer().getName());
	}

	
}
