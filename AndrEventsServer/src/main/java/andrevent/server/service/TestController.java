package andrevent.server.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getTest(@PathVariable String name, ModelMap model) {
		model.addAttribute("name", name);
		return "liste";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getDefaultTest(ModelMap model) {
		System.out.println("test");
		model.addAttribute("name", "this is default movie");
		return "liste";
 
	}
}
