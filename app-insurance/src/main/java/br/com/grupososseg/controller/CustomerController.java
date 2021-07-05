package br.com.grupososseg.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.grupososseg.model.Customer;
import br.com.grupososseg.repository.CustomerRepository;
import br.com.grupososseg.repository.IUserRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private IUserRepository UserRepository;

    private String add_edit_template="admin/customer/add-edit-customer";
    private String list_template="admin/customer/list-customer";
    private String list_redirect="redirect:/customer/list";

    @GetMapping("/add")
    public String addProductType(Customer customer, Model model){
        model.addAttribute("customer", customer);
        return add_edit_template;
    }

    @GetMapping("/edit/{id}")
    public String editProductType(@PathVariable("id") int id, Model model){
        Customer customer = customerRepository.findById(id).get();

        model.addAttribute("customer", customer);

        return add_edit_template;
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/save")
    public String saveProductType(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, Model model){
        model.addAttribute("customer", customer);

        if(result.hasErrors()){
            return add_edit_template;
        }
        
        UserRepository.save(customer.getUser());
        
        customerRepository.save(customer);

        return list_redirect+"?success";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductType(@PathVariable("id") int id, Model model) {
        customerRepository.deleteById(id);

        return list_redirect+"?deleted";
    }

    @GetMapping("/list")
    public String listProductType(Model model) {
        List<Customer> listCustomer = customerRepository.findAll();
        model.addAttribute("listCustomer", listCustomer);

        return list_template;
    }
}