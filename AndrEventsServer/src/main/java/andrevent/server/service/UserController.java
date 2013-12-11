package andrevent.server.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.Evenement;
import andrevent.server.model.ListediffusionHasUser;
import andrevent.server.model.Recherches;
import andrevent.server.model.User;
import andrevent.server.model.UserHasEvenement;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired (required=true)
	private UserJpaController userJpaController;

    /*@PostConstruct
    public void load() {
        emf = Persistence.createEntityManagerFactory("PRTPU");
        userJpaController = new UserJpaController(utx, emf);
    }*/
	
	/*@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getUser(@PathVariable String name, andrevent.server.modelMap andrevent.server.model) {
		model.addAttribute("name", "User" + name);
		return "liste";
	}*/
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody User getUser(@RequestParam(value="id", required=true, defaultValue="0") Integer id) {
		if(id != null)
			return userJpaController.findUser(id);
		
		User user = new User(id);
		
		return user;
    }
	
	@RequestMapping(value = "/user/login/{login}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String login) {
		if(!login.equals(null))
			return userJpaController.findUserByLogin(login);
		
		User user = new User();
		
		return user;
    }
}
