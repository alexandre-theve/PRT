package andrevent.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import andrevent.server.jpacontroller.EvenementJpaController;
import andrevent.server.jpacontroller.TagsJpaController;
import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.Evenement;
import andrevent.server.model.Tags;
import andrevent.server.model.User;

@Controller
public class TagsController {
	private static final Logger logger = Logger.getLogger(EventController.class);

	@Autowired (required=true)
	private TagsJpaController tagsJpaController;
	
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
    public @ResponseBody List<Tags> getTags(Model model) {
		logger.info("getting tags");
		
		return tagsJpaController.findTagsEntities();
    }
}
