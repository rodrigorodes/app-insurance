package br.com.grupososseg.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import br.com.grupososseg.core.util.UserUtils;

@Audited
@AuditTable(value = "tb_insurance_audit")
@Entity
@Table(name = "tb_contract")
public class Contract {

	@Id
	@Column(name = "co_seq_contract", length = 9, nullable = false)
	@GeneratedValue(generator = "sq_contract", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(sequenceName = "sq_contract", name = "sq_contract", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "no_name")
	private String contractName;

	@Column(name = "ds_detail")
	private String contractDetail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "co_user_admin", referencedColumnName = "co_seq_user", nullable = false)
	private User userAdmin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "co_user_influencer", referencedColumnName = "co_seq_user", nullable = false)
	private User userInfluencer;

	@Enumerated(EnumType.STRING)
	@JoinColumn(nullable = false)
	@Column(name = "active")
	private YesNo active = YesNo.Y;
	
	@Enumerated(EnumType.STRING)
	@JoinColumn(nullable = false)
	@Column(name = "ds_type_deal")
	private TypeDeal typeDeal;

	@Column(name = "dt_create")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Column(name = "dt_update")
	private LocalDateTime updatedOn;
	
	protected Contract() {}

	public Contract(String contractName, String contractDetail, User userInfluencer, TypeDeal typeDeal) {
		this.contractName = contractName;
		this.contractDetail = contractDetail;
		this.userInfluencer = userInfluencer;
		this.typeDeal = typeDeal;
	}

	@PrePersist
	public void prePersist() {
		userAdmin = UserUtils.getUserLogged();
	}

	@PreUpdate
	public void preUpdate() {
		updatedOn = LocalDateTime.now();
		userAdmin = UserUtils.getUserLogged();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
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

	public User getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(User userAdmin) {
		this.userAdmin = userAdmin;
	}

	public User getUserInfluencer() {
		return userInfluencer;
	}

	public void setUserInfluencer(User userInfluencer) {
		this.userInfluencer = userInfluencer;
	}

	public YesNo getActive() {
		return active;
	}

	public void setActive(YesNo active) {
		this.active = active;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public TypeDeal getTypeDeal() {
		return typeDeal;
	}

	public void setTypeDeal(TypeDeal typeDeal) {
		this.typeDeal = typeDeal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
