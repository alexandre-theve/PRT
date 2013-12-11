package andrevent.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.User;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired (required=true)
	private UserJpaController userJpaController;

	/*@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getUser(@PathVariable String name, andrevent.server.modelMap andrevent.server.model) {
		model.addAttribute("name", "User" + name);
		return "liste";
	}*/
	
	@RequestMapping(value = "/user/id/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable Integer id) {
		logger.info("getting user by id : " + id);
		if(id != null)
			return userJpaController.findUser(id);

		return new User();
    }
	
	@RequestMapping(value = "/user/login/{login}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String login) {
		logger.info("getting user by login : " + login);
		if(!login.equals(null))
			return userJpaController.findUserByLogin(login);

		return new User();
    }
}
