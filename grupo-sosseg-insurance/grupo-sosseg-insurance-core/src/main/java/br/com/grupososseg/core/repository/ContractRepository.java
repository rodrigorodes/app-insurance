package br.com.grupososseg.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import br.com.grupososseg.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>, RevisionRepository<Contract, Long, Long>{

	 Optional<Contract> getByUserAdmin_Id(Long id);
}
