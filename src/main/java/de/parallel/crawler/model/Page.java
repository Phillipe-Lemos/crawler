package de.parallel.crawler.model;

public class Page {
	
	public static String URL_PREFIX = "https://en.wikipedia.org";
	
	private static String SLASH = "/";
	
	private static String POUND = "#";
	
	private String url;
	
	private Boolean isVisited;
	
	private Long counter;

	public String getUrl() {
		return url;
	}

	public Boolean getIsVisited() {
		return isVisited;
	}

	public Long getCounter() {
		return counter;
	}
	
	public void incrementCounter() {
		this.counter += 1;
	}
	
	public void markVisited() {
		this.isVisited = Boolean.TRUE;
	}

	public Page(String url) {
		super();
		if(!url.startsWith(URL_PREFIX) && url.startsWith(SLASH)) {
			this.url = URL_PREFIX + url;
		} else if(!url.startsWith(URL_PREFIX) && url.startsWith(POUND)) {
			this.url = URL_PREFIX + url;
		} else {
			this.url = url;	
		}
		this.counter = 1L;
		this.isVisited = Boolean.FALSE;
	}
	
	public String toString() {
		return "counter : " + counter + " url : " + url; 
	}
}
