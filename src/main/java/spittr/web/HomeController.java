package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spittr.dao.SpitterRepository;
import spittr.dto.Spitter;
import spittr.service.UserContext;

@Controller
@RequestMapping("/")
public class HomeController {
	private SpitterRepository spitterRepository;

	@Autowired
	public HomeController(SpitterRepository spitterRepository) {
		this.spitterRepository = spitterRepository;
	}

	@RequestMapping(method = GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = "login", method = GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "login", method = POST)
	public String login(Spitter spitter) {
		Spitter longinUser = spitterRepository.findByUsername(spitter.getUsername());
		UserContext.reset(longinUser.getId());
		return "home";
	}

}
