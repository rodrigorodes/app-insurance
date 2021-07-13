package br.com.grupososseg.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.grupososseg.core.dto.CustomerDTO;
import br.com.grupososseg.core.repository.CustomerRepository;
import br.com.grupososseg.model.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private String add_edit_template="admin/customer/add-edit-customer";
	private String list_template="admin/customer/list-customer";
	private String list_redirect="redirect:/customer/list";
	
    private CustomerRepository customerRepository;
    private CustomerValidator customerValidator;

    public CustomerController(CustomerRepository customerRepository, CustomerValidator customerValidator) {
		this.customerRepository = customerRepository;
		this.customerValidator = customerValidator;
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(customerValidator);
	}

	@GetMapping("/add")
    public String add(CustomerDTO customerDTO, Model model){
        model.addAttribute("customerDTO", customerDTO);
        return add_edit_template;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        Customer customer = customerRepository.findById(id).get();

        model.addAttribute("customerDTO", new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail()));

        return add_edit_template;
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/save")
    public String save(
    		@Valid @ModelAttribute("customerDTO") CustomerDTO customerDTO,
    		BindingResult result, 
    		Model model){
		
        model.addAttribute("customerDTO", customerDTO);

        if(result.hasErrors()){
            return add_edit_template;
        }
        
        customerRepository.save(customerDTO.toModel());

        return list_redirect+"?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
    	
    	Optional<Customer> customerOptional = customerRepository.findById(id);
    	
        customerRepository.save(customerOptional.get());

        return list_redirect+"?deleted";
    }

    @GetMapping("/list")
    public String list(Model model) {
        Iterable<Customer> listCustomer = customerRepository.findAll();
        model.addAttribute("listCustomer", listCustomer);
        return list_template;
    }
}