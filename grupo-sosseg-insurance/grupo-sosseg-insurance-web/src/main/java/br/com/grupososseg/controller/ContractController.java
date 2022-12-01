package br.com.grupososseg.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.grupososseg.core.dto.ContractListDTO;
import br.com.grupososseg.core.dto.CreateContractDTO;
import br.com.grupososseg.core.repository.ContractRepository;
import br.com.grupososseg.core.repository.UserRepository;
import br.com.grupososseg.model.Contract;
import br.com.grupososseg.model.TypeDeal;
import br.com.grupososseg.model.User;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private ContractRepository contractRepository;
    private UserRepository userRepository;

    private String add_edit_template="admin/contract/add-edit-contract";
    private String list_template="admin/contract/list-contract";
    private String list_redirect="redirect:/contract/list";

    public ContractController(ContractRepository insuranceRepository, UserRepository userRepository) {
		this.contractRepository = insuranceRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/add")
    public String add(CreateContractDTO createContractDTO, Model model){
    	
        model.addAttribute("contract", createContractDTO);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("typeDeals", TypeDeal.values());

        return add_edit_template;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model){
    	
        Contract contract = contractRepository.findById(id).get();
        
        CreateContractDTO createContractDTO = new CreateContractDTO(contract);
        
        model.addAttribute("contract", createContractDTO);
        model.addAttribute("users", userRepository.findById(contract.getUserInfluencer().getId()).get());
        model.addAttribute("typeDeals", TypeDeal.values());

        return add_edit_template;
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/save")
    public String save(
    		@Valid @ModelAttribute("contract") CreateContractDTO createContractDTO,
    		BindingResult result, 
    		Model model){
        model.addAttribute("contract", createContractDTO);

        if(result.hasErrors()){
            return add_edit_template;
        }
        
       final TypeDeal typeDeal = TypeDeal.findByName(createContractDTO.getTypeDeal());
        
       final Optional<User> user = userRepository.findById(createContractDTO.getIdInfluencer());
        
        Contract contract = new Contract(
        		createContractDTO.getContractName(), 
        		createContractDTO.getContractDetail(), 
        		user.get(), 
        		typeDeal);
        
        contractRepository.save(contract);
        
        return list_redirect+"?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
    	contractRepository.deleteById(id);

        return list_redirect+"?deleted";
    }

    @GetMapping("/list")
    public String listProduct(Model model) {
        final List<Contract> contracts = contractRepository.findAll();
        final List<ContractListDTO> contractsDtos = contracts.stream()
               .map(contract -> new ContractListDTO(contract))
               .collect(Collectors.toList());
        
        model.addAttribute("listContract", contractsDtos);

        return list_template;
    }
}