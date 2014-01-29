package andrevent.server.helper;

import java.io.IOException;
import java.net.ConnectException;

import com.fasterxml.jackson.databind.ObjectMapper;

import andrevent.server.model.Evenement;
import andrevent.server.model.User;

public class PushHelper {
	public static String GCMSenderId = "585790850303";
	public static String APIKey = "AIzaSyAOA6-e4RtYfiFEB8hNuHFnEjJrgrZ7Vjk";
	
	public static String launchPush(User user, Integer evenement) throws ConnectException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		
		return RESTHelper.POST(RESTHelper.url, APIKey, "registration_id="+user.getPush_id()+"&collapse_key=msg&data.message="+mapper.writeValueAsString(evenement));
	}
}
