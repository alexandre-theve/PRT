package andrevent.server.helper;

import java.io.IOException;
import java.net.ConnectException;

import andrevent.server.model.User;

public class PushHelper {
	public static String GCMSenderId = "585790850303";
	public static String APIKey = "AIzaSyBJe_qRRZgPL4omJBfkZ63t6-Vabl-rT4w";
	
	public static String launchPush(User user) throws ConnectException, IOException{
		return RESTHelper.POST(RESTHelper.url, APIKey, "registration_ids="+user.getPush_id()+"&collapse_key=msg&data.message=test push");
	}
}
