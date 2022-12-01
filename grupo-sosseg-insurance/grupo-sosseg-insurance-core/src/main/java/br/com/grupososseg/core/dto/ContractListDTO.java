package br.com.grupososseg.core.dto;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import br.com.grupososseg.model.Insurance;

public class InsuranceDTO {

	private static final String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	private Integer key;
	private Integer id;
	private String carName;
	private String customerName;
	private String updatedOn;
	private String modifiedUserName;

	public InsuranceDTO() {
	}

	public InsuranceDTO(Optional<Integer> numberRevision, Insurance insurance) {
		this.key = numberRevision.get();
		this.id = insurance.getId();
		this.carName = insurance.getCarName();
		this.customerName = insurance.getCustomer().getName();
		this.updatedOn = DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM).format(insurance.getCreatedOn());
		this.modifiedUserName = insurance.getUser().getName();
	}

	public Integer getKey() {
		return key;
	}

	public String getCarName() {
		return carName;
	}

	public Integer getId() {
		return id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public String getModifiedUserName() {
		return modifiedUserName;
	}
}
