package andrevent.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import andrevent.server.jpacontroller.EvenementJpaController;
import andrevent.server.jpacontroller.TagsJpaController;
import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.Evenement;
import andrevent.server.model.Tags;
import andrevent.server.model.User;
import andrevent.server.model.UserHasEvenement;

@Controller
public class EventController {
	private static final Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired (required=true)
	private UserJpaController userJpaController;
	@Autowired (required=true)
	private EvenementJpaController evenementsJpaController;
	@Autowired (required=true)
	private TagsJpaController tagsJpaController;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getUser(ModelMap model) {
		model.addAttribute("evenements", evenementsJpaController.findEvenementEntities());
		model.addAttribute("name", "Admin");
				
		return "/index";
	}
	
	@RequestMapping(value = "/events/push/{idEvent}", method = RequestMethod.GET)
	public String pushEventNotification(@PathVariable Integer idEvent, ModelMap model) {
		logger.info("pushing event " + idEvent);
		model.addAttribute("name", "Admin");
		
		model.addAttribute("message", "Push notfication envoyée !");
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/events/edit/{idEvent}", method = RequestMethod.GET)
	public String getEditEvent(@PathVariable Integer idEvent, ModelMap model) {
		logger.info("editing event " + idEvent);
		System.out.println("editing event " + idEvent);
		model.addAttribute("event", evenementsJpaController.findEvenement(idEvent));
		
		return "/edit";
	}
	
	@RequestMapping(value = "/events/edit", method = RequestMethod.POST)
	public String editEvent(@ModelAttribute("Evenement") Evenement evenement, ModelMap model) {
		logger.info("editing event " + evenement);
		System.out.println("editing event " + evenement);
		//model.addAttribute("event", evenementsJpaController.findEvenement(idEvent));
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/events", method = RequestMethod.GET)
    public @ResponseBody List<Evenement> getEvenements(Model model) {
		logger.info("getting user events : ");

		return evenementsJpaController.findEvenementEntities();
    }
	
	@RequestMapping(value = "/events/idUser/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Evenement> getEvenementsByUser(Model model, @PathVariable Integer id) {
		logger.info("getting user events by idUser : " + id);
		if(id != null) {
			User user = userJpaController.findUser(id);
			return user.getEvenementList();
		}
		
		return new ArrayList<Evenement>();
    }
	
	@RequestMapping(value = "/events/location/latitude/{latitude}/longitude/{longitude}/rayon/{rayon}", method = RequestMethod.GET)
    public @ResponseBody List<Evenement> getEvenementsByLocation(Model model, @PathVariable Double latitude, @PathVariable Double longitude, @PathVariable Double rayon) {
		logger.info("getting events by location : " + latitude + "," + longitude + " - " + rayon);
		
		return evenementsJpaController.findEvenementByLocation(latitude, longitude, rayon);
    }
	
	@RequestMapping(value = "/events/tag/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Evenement> getEvenementsByTag(Model model, @PathVariable Integer id) {
		logger.info("getting events by tag : " + id);
		if(id != null) {
			Tags tag = tagsJpaController.findTags(id);
			return tag.getEvenementList();
		}
		
		return new ArrayList<Evenement>();
    }
	
	@RequestMapping(value = "/events/tags", method = RequestMethod.POST)
    public @ResponseBody List<Evenement> getEvenementsByTags(Model model, @RequestBody ArrayList<Integer> tags) {
		logger.info("getting events by tags : " + tags);
		if(tags != null) {
			return evenementsJpaController.findByTags(tagsJpaController.findTags(tags));
		}
		
		return new ArrayList<Evenement>();
    }
	
	@RequestMapping(value = "/events/suggestionTags/idUser/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Tags> getSuggestionTags(Model model, @PathVariable Integer id) {
		logger.info("getting suggestion events for user : " + id);
		if(id != null) {
			return evenementsJpaController.findTagsForUser(id);
		}
		
		return new ArrayList<Tags>();
    }
	
	@RequestMapping(value = "/events/suggestion/idUser/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Evenement> getSuggestionEvenements(Model model, @PathVariable Integer id) {
		logger.info("getting suggestion events for user : " + id);
		if(id != null) {
			User user = userJpaController.findUser(id);
			List<Evenement> evenements = new ArrayList<Evenement>();
			for(UserHasEvenement e : user.getUserHasEvenementList())
				evenements.add(e.getEvenement());
			return evenementsJpaController.findEvenementsForUser(id, evenements);
		}
		
		return new ArrayList<Evenement>();
    }
	
	@RequestMapping(value = "/events/inscription/user/{idUser}/event/{idEvent}/push/{push}", method = RequestMethod.POST)
    public @ResponseBody User subscribeToEvent(Model model, @PathVariable Integer idUser, @PathVariable Integer idEvent, @PathVariable Boolean push) {
		logger.info("subscribing user " + idUser + " to event : " + idEvent);
		if(idUser != null && idEvent != null) {
			User user = userJpaController.subscribeToEvent(idUser, idEvent, push);
			return user;
		}
		
		return new User();
    }
	
	@RequestMapping(value = "/events/inscription/user/{idUser}/event/{idEvent}", method = RequestMethod.DELETE)
    public @ResponseBody User unsubscribeToEvent(Model model, @PathVariable Integer idUser, @PathVariable Integer idEvent) {
		logger.info("unsubscribing user " + idUser + " to event : " + idEvent);
		if(idUser != null && idEvent != null) {
			User user = userJpaController.unsubscribeToEvent(idUser, idEvent);
			return user;
		}
		
		return new User();
    }
	
	@RequestMapping(value = "/events/inscription/user/{idUser}/event/{idEvent}/push/{push}", method = RequestMethod.PUT)
    public @ResponseBody Boolean editSubscriptionToEvent(Model model, @PathVariable Integer idUser, @PathVariable Integer idEvent, @PathVariable Boolean push) {
		logger.info("editing subscription of user " + idUser + " to event : " + idEvent);
		if(idUser != null && idEvent != null) {
			Boolean result = userJpaController.editSubscriptionToEvent(idUser, idEvent, push);
			return result;
		}
		
		return false;
    }
}
