package br.com.grupososseg.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import br.com.grupososseg.model.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer>, RevisionRepository<Insurance, Integer, Integer>{

	 Optional<Insurance> getByCustomer_Id(Integer id);
}
