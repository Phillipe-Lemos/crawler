package de.parallel.crawler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.parallel.crawler.model.Page;
/**
 * A immutable class responsable for link extraction.
 * 
 * @author phillipe
 *
 */
public class ParseHtmlPage {
	
	private static String REGEX = "<a\\s+href=(.*?)\\s+[>|/>]"; 
	
	private static String REGE_HREF = "href=\"(.*?)\"";
	
	private RetrieveHtmlPage fetcher;
	
	public ParseHtmlPage(RetrieveHtmlPage fetcher) {
		this.fetcher = fetcher;
	}
	
	public Map<String, Page> parseURLPage() {
		final Map<String, Page> addedSeeds = new HashMap<>();
		final StringBuilder sb =  fetcher.fetchHtmlFromUrl();
    	if(sb.length() > 0) {
    		Pattern patter = Pattern.compile(REGEX);
    		Matcher matcher = patter.matcher(sb.toString()); 
    		while(matcher.find()) {
    			String link = matcher.group();
				final Pattern patternHref = Pattern.compile(REGE_HREF);
				final Matcher matcherLink = patternHref.matcher(link);
				if(matcherLink.find()) {
					final String linkReal = matcherLink.group(1);
					Page page = addedSeeds.get(linkReal);
					if(page != null) {
						page.incrementCounter();
					} else {
						addedSeeds.put(linkReal, new Page(linkReal));	 
					}
				}
    		 }
    	}
		return addedSeeds;
	}

}
