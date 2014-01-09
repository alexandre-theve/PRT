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
import andrevent.server.jpacontroller.ListediffusionJpaController;
import andrevent.server.jpacontroller.TagsJpaController;
import andrevent.server.jpacontroller.UserJpaController;
import andrevent.server.model.Evenement;
import andrevent.server.model.Listediffusion;
import andrevent.server.model.Tags;
import andrevent.server.model.User;

@Controller
public class ListeDiffusionController {
	private static final Logger logger = Logger.getLogger(ListeDiffusionController.class);
	
	@Autowired (required=true)
	private UserJpaController userJpaController;
	@Autowired (required=true)
	private EvenementJpaController evenementsJpaController;
	@Autowired (required=true)
	private ListediffusionJpaController listediffusionJpaController;
	
	@RequestMapping(value = "/listes", method = RequestMethod.GET)
    public @ResponseBody List<Listediffusion> getListeDiffusion(Model model) {
		logger.info("getting listes diffusion");
		
		return listediffusionJpaController.findListediffusionEntities();
    }
	
	@RequestMapping(value = "/listes/inscription/user/{idUser}/liste/{idListe}/push/{push}", method = RequestMethod.POST)
    public @ResponseBody User subscribeToListe(Model model, @PathVariable Integer idUser, @PathVariable Integer idListe, @PathVariable Boolean push) {
		logger.info("subscribing user " + idUser + " to liste : " + idListe);
		if(idUser != null && idListe != null) {
			User user = userJpaController.subscribeToListe(idUser, idListe, push);
			return user;
		}
		
		return new User();
    }
	
	@RequestMapping(value = "/listes/inscription/user/{idUser}/liste/{idListe}", method = RequestMethod.DELETE)
    public @ResponseBody User unsubscribeToListe(Model model, @PathVariable Integer idUser, @PathVariable Integer idListe) {
		logger.info("unsubscribing user " + idUser + " to liste : " + idListe);
		if(idUser != null && idListe != null) {
			User user = userJpaController.unsubscribeToListe(idUser, idListe);
			return user;
		}
		
		return new User();
    }

	@RequestMapping(value = "/listes/inscription/user/{idUser}/liste/{idListe}/push/{push}", method = RequestMethod.PUT)
    public @ResponseBody Boolean editSubscriptionToListe(Model model, @PathVariable Integer idUser, @PathVariable Integer idListe, @PathVariable Boolean push) {
		logger.info("editing subscription of user " + idUser + " to event : " + idListe);
		if(idUser != null && idListe != null) {
			Boolean result = userJpaController.editSubscriptionToListe(idUser, idListe, push);
			return result;
		}
		
		return false;
    }
}
