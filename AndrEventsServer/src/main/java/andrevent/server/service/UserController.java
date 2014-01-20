package andrevent.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import andrevent.server.jpacontroller.RecherchesJpaController;
import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.Recherches;
import andrevent.server.model.User;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired (required=true)
	private UserJpaController userJpaController;
	@Autowired (required=true)
	private RecherchesJpaController recherchesJpaController;

	@RequestMapping(value = "/user/id/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(Model model, @PathVariable Integer id) {
		logger.info("getting user by id : " + id);
		if(id != null)
			return userJpaController.findUser(id);

		return new User();
    }
	
	@RequestMapping(value = "/user/login/{login}", method = RequestMethod.GET)
    public @ResponseBody User getUser(Model model, @PathVariable String login) {
		logger.info("getting user by login : " + login);
		if(!login.equals(null))
			return userJpaController.findUserByLogin(login);

		return new User();
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody User addUser(Model model, @RequestBody User user) {
		logger.info("adding user : " + user);
		if(user != null)
			userJpaController.create(user);
		
		return user;
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
    public @ResponseBody Boolean editUser(Model model, @RequestBody User user) {
		logger.info("editing user : " + user);
		if(user != null)
			userJpaController.edit(user);
		
		return true;
    }
	
	@RequestMapping(value = "/user/id/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Boolean deleteUser(Model model, @PathVariable Integer id) {
		logger.info("deleting user by id : " + id);
		userJpaController.destroy(id);

		return true;
    }
	
	@RequestMapping(value = "/user/recherche", method = RequestMethod.POST)
    public @ResponseBody Recherches addUserSearch(Model model, @RequestBody Recherches recherche) {
		logger.info("adding user search : " + recherche);
		if(recherche != null)
			recherchesJpaController.create(recherche);

		return recherche;
    }
}
