package br.com.grupososseg.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;

import br.com.grupososseg.core.dto.InsuranceDTO;
import br.com.grupososseg.core.repository.CustomerRepository;
import br.com.grupososseg.core.repository.InsuranceRepository;
import br.com.grupososseg.model.Customer;
import br.com.grupososseg.model.Insurance;

@Service
public class InsuranceAuditService {
	
	@Autowired
	private InsuranceRepository insuranceRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<InsuranceDTO> findAllByDocument(String document){
		
		if(StringUtils.isBlank(document)) {
			return new ArrayList<InsuranceDTO>();
		}
		
		Optional<Customer> customerOptional = customerRepository.findByEmail(document);
		
		if(!customerOptional.isPresent()) {
			return new ArrayList<InsuranceDTO>();
		}
		
		Optional<Insurance> insuranceOptional = insuranceRepository.getByCustomer_Id(customerOptional.get().getId());
		
		if(!insuranceOptional.isPresent()) {
			return new ArrayList<InsuranceDTO>();
		}

		Revisions<Integer, Insurance> findRevisions = insuranceRepository.findRevisions(insuranceOptional.get().getId());
		
		List<InsuranceDTO> insuranceDTO = new ArrayList<InsuranceDTO>();
		
		findRevisions
				.toList()
				.forEach(c -> insuranceDTO.add(new InsuranceDTO(c.getRevisionNumber(), c.getEntity())));
		
		return insuranceDTO;
		
	}
	
	public InsuranceDTO findByRevisionNumber(Integer key, Integer id) {
		Optional<Revision<Integer, Insurance>> findRevision = insuranceRepository.findRevision(id,key);
		
		if(!findRevision.isPresent()) {
			return new InsuranceDTO();
		}
		
		Revision<Integer, Insurance> revision = findRevision.get();
		
		return new InsuranceDTO(revision.getRevisionNumber(), revision.getEntity());
	}
}
