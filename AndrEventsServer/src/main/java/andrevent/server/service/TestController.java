package andrevent.server.service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger logger = Logger.getLogger(TestController.class);
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getTest(@PathVariable String name, ModelMap model) {
		System.out.println("test " + name);
		logger.log(Level.INFO, "Message d'information");
		model.addAttribute("name", name);
		return "liste";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getDefaultTest(ModelMap model) {
		System.out.println("test");
		logger.log(Level.INFO, "Message d'information");
		model.addAttribute("name", "this is default movie");
		return "liste";
 
	}
}
