package br.com.grupososseg.core.dto;

import br.com.grupososseg.model.Contract;

public class CreateContractDTO {

	private Long id;
	private String contractName;
	private String contractDetail;
	private Long idInfluencer;
	private String typeDeal;
	
	public CreateContractDTO() {}

	public CreateContractDTO(Contract contract) {
		this.id = contract.getId();
		this.contractName = contract.getContractName();
		this.contractDetail = contract.getContractDetail();
		this.idInfluencer = contract.getUserInfluencer().getId();
		this.typeDeal = contract.getTypeDeal().getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractDetail() {
		return contractDetail;
	}

	public void setContractDetail(String contractDetail) {
		this.contractDetail = contractDetail;
	}

	public Long getIdInfluencer() {
		return idInfluencer;
	}

	public void setIdInfluencer(Long idInfluencer) {
		this.idInfluencer = idInfluencer;
	}

	public String getTypeDeal() {
		return typeDeal;
	}

	public void setTypeDeal(String typeDeal) {
		this.typeDeal = typeDeal;
	}

}
