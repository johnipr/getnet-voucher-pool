package com.getnet.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getnet.app.dto.VoucherDtoRequest;
import com.getnet.app.dto.VoucherDtoResponse;
import com.getnet.app.model.Voucher;
import com.getnet.app.service.VoucherService;

@RestController
@RequestMapping("vouchers")
public class VoucherController {
	
	@Autowired
	private VoucherService voucherService;
	
	@PostMapping("/redeem")
	public VoucherDtoResponse redeem(@Valid @RequestBody VoucherDtoRequest voucherDTO) {

		Voucher voucher = voucherService.redeem(voucherDTO.getEmail(), voucherDTO.getCode());

		return VoucherDtoResponse.convertoModelToDto(voucher);
	}

	@GetMapping("/email/{email}")
	public List<VoucherDtoResponse> findByEmail(@PathVariable("email") @NotBlank String email) {

		List<Voucher> vouchers = voucherService.findAllByEmail(email);
		
		List<VoucherDtoResponse> vouchersDto = vouchers.stream()
			.map(voucher -> VoucherDtoResponse.convertoModelToDto(voucher))
			.collect(Collectors.toList());

		return vouchersDto;
	}
	
	@GetMapping("/{code}")
	public VoucherDtoResponse findByCode(@PathVariable("code") @NotBlank String code) {

		Voucher voucher = voucherService.findByCode(code);

		return VoucherDtoResponse.convertoModelToDto(voucher);
	}
	
	@GetMapping()
	public List<VoucherDtoResponse> findAll() {

		List<Voucher> vouchers = voucherService.findAll();

		List<VoucherDtoResponse> vouchersDto = vouchers.stream()
			.map(voucher -> VoucherDtoResponse.convertoModelToDto(voucher))
			.collect(Collectors.toList());

		return vouchersDto;
	}

}
