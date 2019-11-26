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

import sopra.formation.model.Dispositif;
import sopra.formation.model.Filiere;
import sopra.formation.model.Formateur;
import sopra.formation.repository.IFiliereRepository;
import sopra.formation.repository.IPersonneRepository;

@Controller
@RequestMapping("/filiere")
public class FiliereController {

	@Autowired
	private IFiliereRepository filiereRepo;

	@Autowired
	private IPersonneRepository personneRepo;

	public FiliereController() {
		super();
	}

	@GetMapping("")
	public String home() {
		return "forward:list";
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<Filiere> filieres = filiereRepo.findAll();

		model.addAttribute("filieres", filieres);

		return "filiere/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
//		Pour initialiser le formulaire avec des données par défaut (ex : version = 0)
		model.addAttribute("filiere", new Filiere());
		model.addAttribute("dispositif", Dispositif.values());
		model.addAttribute("referents", personneRepo.findAllFormateur());

		return "filiere/form";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, Model model) {
		model.addAttribute("filiere", filiereRepo.findById(id).get());
		model.addAttribute("dispositif", Dispositif.values());
		model.addAttribute("referents", personneRepo.findAllFormateur());

		return "filiere/form";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("filiere") @Valid Filiere filiere, BindingResult result, Model model,
			@RequestParam("referent.id") Long id) {

		if (result.hasErrors()) {

			model.addAttribute("dispositif", Dispositif.values());
			model.addAttribute("referents", personneRepo.findAllFormateur());

			return "filiere/form";
		}

		if (filiere.getReferent().getId() == null) {
			filiere.setReferent(null);
		}

		filiereRepo.save(filiere);

		return "redirect:/filiere/list";
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "forward:/filiere/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, Model model) {
		filiereRepo.deleteById(id);

		return list(model);
	}

}
