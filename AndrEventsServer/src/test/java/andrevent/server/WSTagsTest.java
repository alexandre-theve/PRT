package andrevent.server;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import andrevent.server.model.Tags;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WSTagsTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() {
		//tags
		List<Tags> tags = getTags();
		System.out.println("Tags : " + tags);
		assertTrue(tags.size() != 0);
	}
	
	public List<Tags> getTags(){
		String JSON;
		try {
			JSON = RESTHelper.GET(RESTHelper.url+"/AndrEventServer/tags");
			
			List<Tags> tags = mapper.readValue(JSON, new TypeReference<List<Tags>>(){});
			
			return tags;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ConnectException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException");
		}	
		
		return new ArrayList<Tags>();
	}
}
