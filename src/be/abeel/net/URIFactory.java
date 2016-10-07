/**
 * %HEADER%
 */
package be.abeel.net;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 
 * @author Thomas Abeel
 * 
 */
public class URIFactory {

	public static URL url(String s) throws MalformedURLException,
			URISyntaxException {
		return magicURI(s).toURL();
	}

	/* URL reserved symbols */
	private static String[] special = new String[] { "%21", "%23", "%24",
			"%26", "%27", "%28", "%29", "%2A", "%2B", "%2C", "%2F", "%3A",
			"%3B", "%3D", "%3F", "%40", "%5B", "%5D" };

	/**
	 * Will build a URI after decoding any encoded reserved characters.
	 * 
	 * This will make sure any URL that accidently got encoded multiple times
	 * gets read properly.
	 * 
	 */
	public static URI magicURI(String urlString) throws MalformedURLException,
			URISyntaxException {
		boolean contains = true;
		while (contains) {
			contains=false;
			for (String s : special) {
				if (urlString.contains(s))
					contains = true;
			}
			if (contains) {
				/* Try one round of decoding */
				try {
					urlString = URLDecoder.decode(urlString, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// This should not happen, I'm fairly certain UTF-8 exists.
					System.err
							.println("Apparently I was wrong and UTF-8 does not exist after all.");
					throw new RuntimeException(e);

				}
			}
		}
		return rawURI(urlString);

	}
	/**
	 * Use rawURI or magicURI
	 * 
	 * This method is now a proxy of magicURI. 
	 * 
	 * @param urlString
	 * @return
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 */
	public static URI uri(String urlString) throws MalformedURLException, URISyntaxException{
		return magicURI(urlString);
		
	}

	/**
	 * Will convert the provide string directly to a URI.
	 * 
	 * @param string
	 * @return
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	public static URI rawURI(String urlString) throws MalformedURLException,
			URISyntaxException {
		URL url = new URL(urlString);
		String host = url.getHost();
		String protocol = url.getProtocol();
		String path = url.getPath();
		String query = url.getQuery();
		String user = url.getUserInfo();
		int port = url.getPort();
		String anchor = url.getRef();
		return new URI(protocol, user, host, port, path, query, anchor);
	}
}
