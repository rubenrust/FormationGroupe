package sopra.formation.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sopra.formation.model.Formateur;
import sopra.formation.repository.IPersonneRepository;

@Controller
@RequestMapping("/formateur")
public class FormateurController {
	
	
	@Autowired
	private IPersonneRepository personneRepo;
	


	public FormateurController() {
		super();
	}

	@GetMapping({ "", "/list" })
	public String list(Model model) {
		List<Formateur> formateurs = personneRepo.findAllFormateur();

		model.addAttribute("formateurs", formateurs);

		return "formateur/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		
		model.addAttribute("formateur", new Formateur());
//		model.addAttribute("matieres", matiereRepo.findAllByFormateur(null));
		return "formateur/form";
		
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, Model model) {
		model.addAttribute("formateur", personneRepo.findById(id).get());
	//	model.addAttribute("niveauEtudes", NiveauEtude.values());


		return "formateur/form";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("formateur") @Valid Formateur formateur, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			
			
			return "formateur/form";
		}
		
		personneRepo.save(formateur);

		return "redirect:/formateur/list";
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "forward:/formateur/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long idFormateur, Model model) {
		personneRepo.deleteById(idFormateur);

		return list(model);
	}

}
