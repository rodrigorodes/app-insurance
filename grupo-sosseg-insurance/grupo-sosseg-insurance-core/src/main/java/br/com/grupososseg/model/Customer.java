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
@AuditTable(value = "tb_customer_audit")
@Entity
@Table(name = "tb_customer")
public class Customer {

	@Id
	@Column(name = "co_seq_customer", length = 9, nullable = false)
	@GeneratedValue(generator = "sq_customer", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(sequenceName = "sq_customer", name = "sq_customer", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Enumerated(EnumType.STRING)
	@JoinColumn(nullable = false)
	@Column(name = "active")
	private YesNo active = YesNo.Y;

	@Column(name = "dt_create")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Column(name = "dt_update")
	private LocalDateTime updatedOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "co_user", referencedColumnName = "co_seq_user", nullable = false)
	private User user;
	
	public Customer() {
	}
	
	public Customer(Integer id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	@PrePersist
	public void prePersist() {
		user = UserUtils.getUserLogged();
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedOn = LocalDateTime.now();
		user = UserUtils.getUserLogged();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public YesNo getActive() {
		return active;
	}

	public void setActive(YesNo active) {
		this.active = active;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
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
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
