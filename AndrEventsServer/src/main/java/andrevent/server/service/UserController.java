package andrevent.server.service;

import javax.enterprise.inject.Produces;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import andrevent.server.model.User;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	/*@Autowired (required=true)
	private UserJpaController userJpaController;*/

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
		//if(id != null)
			//return userJpaController.findUser(id);
		
		return new User(id);
    }
}
