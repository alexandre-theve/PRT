package helpers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RESTHelper {
	public static String GET(String URL) throws IOException,ConnectException {

		InputStream in = null;
		try {
			final URL url = new URL(URL);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setConnectTimeout(15000);
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
	
	public static String POST_PUT(String URL,String method,String dataToPost) throws IOException,ConnectException {

		InputStream in = null;
		try {
			final URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setConnectTimeout(15000);
			urlConnection.setRequestMethod(method);
			urlConnection.setRequestProperty("Content-Type","application/json");
			urlConnection.connect();
			OutputStream os = urlConnection.getOutputStream();
			os.write(dataToPost.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			return reader.readLine();
		}catch (MalformedURLException e) {
			e.printStackTrace();
			return "URL INCORRECTE";
		}
		catch(FileNotFoundException e){
			
			throw new ConnectException("Erreur 40x : " + e.getMessage());
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
