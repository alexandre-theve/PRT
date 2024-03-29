package helpers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RESTHelper {
	public static String GET(String URL) throws IOException,ConnectException {

		InputStream in = null;
		try {
			final URL url = new URL(URL);
			URLConnection urlConnection = url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			return reader.readLine();
		}catch (MalformedURLException e) {
			e.printStackTrace();
			return "URL INCORRECTE";
		}
		catch(FileNotFoundException e){
			throw new ConnectException("Erreur 40x");
		}
		catch (Exception e){
			e.printStackTrace();
			return "OTHER EXCEPTION";
		}
		finally {
			if (in != null){
				in.close();
			}
			
		}

	}
}
