package andrevent.server.service;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import andrevent.server.helper.PushHelper;
import andrevent.server.jpacontroller.EvenementJpaController;
import andrevent.server.jpacontroller.TagsJpaController;
import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.Evenement;
import andrevent.server.model.UserHasEvenement;

@Controller
public class BackController {
	private static final Logger logger = Logger.getLogger(BackController.class);
	
	@Autowired (required=true)
	private UserJpaController userJpaController;
	@Autowired (required=true)
	private EvenementJpaController evenementsJpaController;
	@Autowired (required=true)
	private TagsJpaController tagsJpaController;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getUser(@ModelAttribute("message") String message, ModelMap model) {
		model.addAttribute("evenements", evenementsJpaController.findEvenementEntities());
		model.addAttribute("name", "Admin");
		model.addAttribute("message", message);
				
		return "/index";
	}
	
	@RequestMapping(value = "/events/push/{idEvent}", method = RequestMethod.GET)
	public String pushEventNotification(@PathVariable Integer idEvent, RedirectAttributes model) {
		logger.info("pushing event " + idEvent);
		
		Evenement evenement = evenementsJpaController.findEvenement(idEvent);
		for (UserHasEvenement userHasEvenement : evenement.getUserHasEvenementList()) {
			if(userHasEvenement.getNotifications() && userHasEvenement.getUser().getPush_id() != null && !userHasEvenement.getUser().getPush_id().equals("")) {
				try {
					String response = PushHelper.launchPush(userHasEvenement.getUser(), evenement);
					
					logger.info("response :  " + response);					
				} catch (ConnectException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		model.addFlashAttribute("message", "Push notfication envoyée !");
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/events/edit/{idEvent}", method = RequestMethod.GET)
	public String getEditEvent(@PathVariable Integer idEvent, ModelMap model) {
		logger.info("get editing event " + idEvent);
		
		if(idEvent!=0)
			model.addAttribute("evenement", evenementsJpaController.findEvenement(idEvent));
		else
			model.addAttribute("evenement", new Evenement());
		
		return "/edit";
	}
	
	@RequestMapping(value = "/events/{idEvent}", method = RequestMethod.DELETE)
	public String deleteEvent(@PathVariable Integer idEvent, ModelMap model) {
		logger.info("deleting event " + idEvent);
		
		evenementsJpaController.destroy(idEvent);
		
		model.addAttribute("message", "Evenement supprimé !");
		
		return "redirect:/index";
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(
	            dateFormat, false));
	}
	
	@RequestMapping(value = "/events/edit", method = RequestMethod.POST)
	public String editEvent(@ModelAttribute Evenement evenement, ModelMap m) {
		logger.info("editing event " + evenement);
		
		if(evenement.getId() != null) {
		
			Evenement evenementEdited = evenementsJpaController.findEvenement(evenement.getId());
			
			evenementEdited.setNom(evenement.getNom());
			evenementEdited.setLieu(evenement.getLieu());
			evenementEdited.setDateDebut(evenement.getDateDebut());
			evenementEdited.setDateFin(evenement.getDateFin());
			evenementEdited.setLatitude(evenement.getLatitude());
			evenementEdited.setLongitude(evenement.getLongitude());
			evenementEdited.setDescription(evenement.getDescription());
			
			evenementsJpaController.edit(evenementEdited);
			
			m.addAttribute("message", "Evenement modifié !");
		} else {
			evenement.setCreateur(userJpaController.findUser(1));
			evenementsJpaController.create(evenement);
			
			m.addAttribute("message", "Evenement créé !");
		}
				
		return "redirect:/index";
	}
	
	@RequestMapping(value="/get", method = RequestMethod.GET)
    public ModelAndView get() {
        return new ModelAndView("evenement", "command", new Evenement());
    }
}
