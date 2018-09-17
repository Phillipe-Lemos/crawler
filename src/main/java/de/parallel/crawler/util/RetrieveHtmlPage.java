package de.parallel.crawler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Immutable class responsable to retrieve html from an URL. The URL is represented by
 * a String url.
 *  
 * @author phillipe
 *
 */
public class RetrieveHtmlPage {
	
	private final String url;
	
	public RetrieveHtmlPage(final String url) {
		this.url = url;
	}
	
	public StringBuilder fetchHtmlFromUrl() {
    	final StringBuilder sb = new StringBuilder();
    	try {
    		final URL endPoint = new URL(url);
    		try(final InputStream in = endPoint.openStream();
    		    final BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
    		}catch(IOException io) {
    			throw new RuntimeException(io);
    		}
    	} catch (MalformedURLException exception) {
    		throw new IllegalArgumentException(exception);
    	}
    	return sb;
	}
}
