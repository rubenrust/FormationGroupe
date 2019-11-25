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

import sopra.formation.model.Matiere;
import sopra.formation.model.MatiereId;
import sopra.formation.model.NiveauMatiere;
import sopra.formation.repository.IMatiereRepository;

@Controller
@RequestMapping("/matiere")
public class MatiereController {

	@Autowired
	private IMatiereRepository matiereRepo;
	
	public MatiereController() {
		super();
	}
	
	@GetMapping({"", "/list"})
	public String list(Model model) {
		List<Matiere> matieres = matiereRepo.findAll();
		
		model.addAttribute("matieres", matieres);
		
		return "matiere/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		
		model.addAttribute("niveauMatieres", NiveauMatiere.values());
		model.addAttribute("matiere", new Matiere());
		
		
		Boolean readdable = true;
		
		model.addAttribute("readdable", readdable);
		
		return "matiere/form";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam String nom, @RequestParam NiveauMatiere niveau, Model model) {
		
		MatiereId matiereId = new MatiereId(nom, niveau);
		
		Boolean readdable = false;
		
		model.addAttribute("readdable", readdable);
		model.addAttribute("matiere", matiereRepo.findById(matiereId).get());
		model.addAttribute("niveauMatieres", NiveauMatiere.values());
		
		
		return "matiere/form";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("matiere") @Valid Matiere matiere, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("niveauMatieres", NiveauMatiere.values());
			
			return "matiere/form";
		}
		matiereRepo.save(matiere);
		
		return "redirect:/matiere/list";
	}
	
	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/matiere/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam String nom, @RequestParam NiveauMatiere niveau, @ModelAttribute Matiere matiere, Model model) {
		
		MatiereId matiereId = new MatiereId(nom, niveau);
		
		matiereRepo.deleteById(matiereId);
		
		return "redirect:/matiere/list";
	}
}
