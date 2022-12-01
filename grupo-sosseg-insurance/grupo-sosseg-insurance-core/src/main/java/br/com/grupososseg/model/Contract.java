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
@Table(name = "tb_insurance")
public class Insurance {

	@Id
	@Column(name = "co_seq_insurance", length = 9, nullable = false)
	@GeneratedValue(generator = "sq_insurance", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(sequenceName = "sq_insurance", name = "sq_insurance", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "no_car_name")
	private String carName;

	@Column(name = "ds_car_year")
	private int carYear;

	@Column(name = "ds_car_license")
	private String carLicense;

	@Column(name = "ds_car_detail")
	private String carDetail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "co_customer", referencedColumnName = "co_seq_customer", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "co_user", referencedColumnName = "co_seq_user", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@JoinColumn(nullable = false)
	@Column(name = "active")
	private YesNo active = YesNo.Y;

	@Column(name = "dt_create")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Column(name = "dt_update")
	private LocalDateTime updatedOn;

	@PrePersist
	public void prePersist() {
		user = UserUtils.getUserLogged();
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedOn = LocalDateTime.now();
		user = UserUtils.getUserLogged();
	}

	public Insurance() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getCarYear() {
		return carYear;
	}

	public void setCarYear(int carYear) {
		this.carYear = carYear;
	}

	public String getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}

	public String getCarDetail() {
		return carDetail;
	}

	public void setCarDetail(String carDetail) {
		this.carDetail = carDetail;
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
		Insurance other = (Insurance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
