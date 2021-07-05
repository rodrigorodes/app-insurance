package br.com.grupososseg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
