package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import spittr.dao.SpitterRepository;
import spittr.dto.Spitter;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

	private SpitterRepository spitterRepository;

	@Autowired
	public SpitterController(SpitterRepository spitterRepository) {
		this.spitterRepository = spitterRepository;
	}

	@RequestMapping(value = "/register", method = GET)
	public String showRegistrationForm(Model model) {
		model.addAttribute(new Spitter());
		return "registerForm";
	}

	@RequestMapping(value = "/register", method = POST)
	public String processRegistration(@Valid SpitterForm spitterForm, Errors errors)
			throws IllegalStateException, IOException {

		if (errors.hasErrors()) {
			return "registerForm";
		}
		Spitter spitter = spitterForm.toSpitter();
		spitterRepository.save(spitter);
		return "redirect:/spitter/" + spitter.getUsername();
	}

	@RequestMapping(value = "/{username}", method = GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		if (!model.containsAttribute("spitter")) {
			model.addAttribute(spitterRepository.findByUsername(username));
		}
		return "profile";
	}
}
