package br.com.grupososseg.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.grupososseg.core.dto.InsuranceDTO;
import br.com.grupososseg.core.dto.InsuranceFilterDTO;
import br.com.grupososseg.core.service.InsuranceAuditService;

@Controller
@RequestMapping("/insurance/history")
public class HistoryInsuranceController {

	private final String list_template = "admin/history/list-history-insurance";
	private final String list_template_view = "admin/history/add-view-insurance";

	private InsuranceAuditService insuranceAuditService;

	public HistoryInsuranceController(InsuranceAuditService insuranceAuditService) {
		this.insuranceAuditService = insuranceAuditService;
	}

	@GetMapping("/view")
	public String view(InsuranceFilterDTO insuranceFilterDTO, Model model) {
		model.addAttribute("insuranceFilterDTO", insuranceFilterDTO);
		model.addAttribute("listInsurance", new ArrayList<>());
		return list_template;
	}

	@PostMapping("/search")
	public String search(
			@Valid @ModelAttribute("insuranceFilterDTO") InsuranceFilterDTO insuranceFilterDTO,
			BindingResult result, 
			Model model) {

		model.addAttribute("insuranceFilterDTO", insuranceFilterDTO);
		model.addAttribute("listInsurance", new ArrayList<>());

		if (result.hasErrors()) {
			return list_template;
		}

		model.addAttribute("listInsurance", insuranceAuditService.findAllByDocument(insuranceFilterDTO.getEmail()));

		return list_template;
	}

	@GetMapping("/find")
	public String find(@RequestParam("id") int id, @RequestParam("key") int key, Model model) {
		InsuranceDTO insurance = insuranceAuditService.findByRevisionNumber(key, id);
		model.addAttribute("insurance", insurance);
		return list_template_view;
	}
}
