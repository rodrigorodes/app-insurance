package br.com.grupososseg.core.dto;

import java.time.format.DateTimeFormatter;

import br.com.grupososseg.model.Contract;

public class ContractListDTO {

	private static final String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	private Long id;
	private String contractName;
	private String contractDetail;
	private String typeDeal;
	private String updatedOn;
	private String userInfluencer;

	public ContractListDTO() {
	}

	public ContractListDTO(Contract contract) {
		this.id = contract.getId();
		this.contractName = contract.getContractName();
		this.contractDetail = contract.getContractDetail();
		this.updatedOn = DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM).format(contract.getCreatedOn());
		this.userInfluencer = contract.getUserInfluencer().getName();
		this.typeDeal = contract.getTypeDeal().getName();
	}

	public Long getId() {
		return id;
	}

	public String getContractName() {
		return contractName;
	}

	public String getContractDetail() {
		return contractDetail;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public String getUserInfluencer() {
		return userInfluencer;
	}

	public String getTypeDeal() {
		return typeDeal;
	}
}
