package andrevent.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import andrevent.server.jpacontroller.EvenementJpaController;
import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.Evenement;

@Controller
public class EventController {
	private static final Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired (required=true)
	private UserJpaController userJpaController;
	@Autowired (required=true)
	private EvenementJpaController evenementsJpaController;
	
	@RequestMapping(value = "/events/idUser/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Evenement> getEvenementsByUser(@PathVariable Integer id) {
		logger.info("getting user events by idUser : " + id);
		if(id != null) {
			return userJpaController.findUser(id).getEvenementList();
		}
		
		return new ArrayList<Evenement>();
    }
	
	@RequestMapping(value = "/events/location/latitude/{latitude}/longitude/{longitude}/rayon/{rayon}", method = RequestMethod.GET)
    public @ResponseBody List<Evenement> getEvenementsByLocation(@PathVariable Double latitude, @PathVariable Double longitude, @PathVariable int rayon) {
		logger.info("getting events by location : " + latitude + "," + longitude + " - " + rayon);
		
		return evenementsJpaController.findEvenementByLocation(latitude, longitude, rayon);
    }
}
