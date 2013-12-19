package andrevent.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
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
import java.net.URLEncoder;

public class RESTHelper {
	public static String POST(String URL, String param) throws IOException,
			ConnectException {
		System.out.println("sending " + param + " to " + URL);
		OutputStream out;
		InputStream in = null;
		try {
			final URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection
					.setRequestProperty("Content-Type", "application/json");
			
			urlConnection.setDoOutput(true);
			urlConnection.connect();
			
			out = urlConnection.getOutputStream();
			out.write(param.getBytes());
			
			System.out.println("HTTP:" + out.toString());
			
			out.flush();
			out.close();

			int HttpResult = urlConnection.getResponseCode();

			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String line = null;
				
				StringBuilder sb = new StringBuilder();
				
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				return sb.toString();

			} else {
				System.out.println(urlConnection.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "URL INCORRECTE";
		} catch (FileNotFoundException e) {
			throw new ConnectException("Erreur 40x");
		} catch (Exception e) {
			e.printStackTrace();
			return "OTHER EXCEPTION";
		} finally {
			if (in != null) {
				in.close();
			}
		}
		
		return "";
	}

	public static String GET(String URL) throws IOException, ConnectException {

		InputStream in = null;
		try {
			final URL url = new URL(URL);
			URLConnection urlConnection = url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			return reader.readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "URL INCORRECTE";
		} catch (FileNotFoundException e) {
			throw new ConnectException("Erreur 40x");
		} catch (Exception e) {
			e.printStackTrace();
			return "OTHER EXCEPTION";
		} finally {
			if (in != null) {
				in.close();
			}

		}

	}
}
