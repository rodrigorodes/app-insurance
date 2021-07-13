package br.com.grupososseg.controller;

import java.util.List;

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

import br.com.grupososseg.core.repository.CustomerRepository;
import br.com.grupososseg.core.repository.InsuranceRepository;
import br.com.grupososseg.model.Insurance;

@Controller
@RequestMapping("/insurance")
public class InsuranceController {

    private InsuranceRepository insuranceRepository;
    private CustomerRepository customerRepository;

    private String add_edit_template="admin/insurance/add-edit-insurance";
    private String list_template="admin/insurance/list-insurance";
    private String list_redirect="redirect:/insurance/list";

    public InsuranceController(InsuranceRepository insuranceRepository, CustomerRepository customerRepository) {
		this.insuranceRepository = insuranceRepository;
		this.customerRepository = customerRepository;
	}

	@GetMapping("/add")
    public String add(Insurance insurance, Model model){
    	
        model.addAttribute("insurance", insurance);
        model.addAttribute("customers", customerRepository.findAll());
        
        return add_edit_template;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
    	
        Insurance insurance = insuranceRepository.findById(id).get();
        model.addAttribute("insurance", insurance);
        model.addAttribute("customers", customerRepository.findById(insurance.getCustomer().getId()).get());
        
        return add_edit_template;
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/save")
    public String save(
    		@Valid @ModelAttribute("insurance") Insurance insurance,
    		BindingResult result, 
    		Model model){
        model.addAttribute("insurance", insurance);

        if(result.hasErrors()){
            return add_edit_template;
        }
        insuranceRepository.save(insurance);
        
        return list_redirect+"?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
    	insuranceRepository.deleteById(id);

        return list_redirect+"?deleted";
    }

    @GetMapping("/list")
    public String listProduct(Model model) {
        List<Insurance> listProducts = insuranceRepository.findAll();
        model.addAttribute("listInsurance", listProducts);

        return list_template;
    }
}