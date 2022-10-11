package com.getnet.app.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getnet.app.exception.ConflictException;
import com.getnet.app.exception.NotFoundException;
import com.getnet.app.model.Client;
import com.getnet.app.model.Offer;
import com.getnet.app.model.Voucher;
import com.getnet.app.repository.VoucherRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VoucherService {

	@Autowired
	private VoucherRepository repository;

	public void create(List<Client> clients, Offer offer) {
		clients.stream().forEach((client) -> generateVoucher(client, offer));
	}

	private void generateVoucher(Client client, Offer offer) {
		log.debug(client.getEmail());

		Voucher voucher = new Voucher();
		voucher.setClientName(client.getName());
		voucher.setEmail(client.getEmail());
		voucher.setOffer(offer);

		voucher.setCode(UUID.randomUUID().toString());

		repository.save(voucher);
	}

	public Voucher redeem(String email, String code) {

		Optional<Voucher> voucherOpt = repository.findByEmailAndCode(email, code, LocalDate.now());

		if (voucherOpt.isEmpty()) {
			throw new NotFoundException("Voucher [ " + code + " ] not found");
		}

		Voucher voucherModel = voucherOpt.get();

		if (voucherModel.getRedeemDate() != null) {
			String date = voucherModel.getRedeemDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			throw new ConflictException("This voucher was used on " + date);
		}

		// Set Redeem Date
		voucherModel.setRedeemDate(LocalDate.now());

		// Update with new redeem date
		voucherModel = repository.save(voucherModel);

		return voucherModel;
	}

	public List<Voucher> findAllByEmail(String email) {

		Optional<List<Voucher>> vouchersOpt = repository.findAllByEmail(email, LocalDate.now());

		if (vouchersOpt.isEmpty()) {
			throw new NotFoundException("No vouchers found for this email : " + email);
		}

		return vouchersOpt.get();
	}
	
	public Voucher findByCode(String code) {

		 Optional<Voucher> voucher = repository.findByCode(code);

		 if (voucher.isEmpty()) {
				throw new NotFoundException("No voucher found for this code : " + code);
			}

		return voucher.get();
	}

	public List<Voucher> findAll() {

		List<Voucher> vouchers = repository.findAll();

		if (vouchers.isEmpty()) {
			throw new NotFoundException("No vouchers found ");
		}

		return vouchers;
	}
}
