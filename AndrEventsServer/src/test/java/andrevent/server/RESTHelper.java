package andrevent.server;

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

public class RESTHelper {
	public static String url = "http://localhost:8080";
	
	public static String DELETE(String URL) throws IOException,
			ConnectException {
		System.out.println("sending " + URL);
		InputStream in = null;
		try {
			final URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("DELETE");
			urlConnection
					.setRequestProperty("Content-Type", "application/json");

			urlConnection.setDoOutput(true);
			urlConnection.connect();

			int HttpResult = urlConnection.getResponseCode();

			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String line = null;

				StringBuilder sb = new StringBuilder();

				while ((line = br.readLine()) != null) {
					sb.append(line);
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

	public static String PUT(String URL) throws IOException,
			ConnectException {
		return PUT(URL, "");
	}
	
	public static String PUT(String URL, String param) throws IOException,
			ConnectException {
		System.out.println("sending " + param + " to " + URL);
		OutputStream out;
		InputStream in = null;
		try {
			final URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("PUT");
			urlConnection
					.setRequestProperty("Content-Type", "application/json");

			urlConnection.setDoOutput(true);
			urlConnection.connect();

			out = urlConnection.getOutputStream();
			out.write(param.getBytes());

			out.flush();
			out.close();

			int HttpResult = urlConnection.getResponseCode();

			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String line = null;

				StringBuilder sb = new StringBuilder();

				while ((line = br.readLine()) != null) {
					sb.append(line);
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

	public static String POST(String URL) throws IOException,
			ConnectException {
		return POST(URL, "");
	}
	
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

			out.flush();
			out.close();

			int HttpResult = urlConnection.getResponseCode();

			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String line = null;

				StringBuilder sb = new StringBuilder();

				while ((line = br.readLine()) != null) {
					sb.append(line);
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
		System.out.println("sending to " + URL);
		InputStream in = null;
		try {
			final URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection
					.setRequestProperty("Content-Type", "application/json");

			urlConnection.connect();
			
			int HttpResult = urlConnection.getResponseCode();

			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String line = null;

				StringBuilder sb = new StringBuilder();

				while ((line = br.readLine()) != null) {
					sb.append(line);
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
}
