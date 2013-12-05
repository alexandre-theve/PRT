package andrevent.server.service;

import model.User;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	/*@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getUser(@PathVariable String name, ModelMap model) {
		model.addAttribute("name", "User" + name);
		return "liste";
	}*/
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody User getUser(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new User("42", name);
    }
}
