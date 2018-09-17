package de.parallel.crawler.util;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.fail;

import org.junit.Test;

public class RetrieveHtmlPageTest {
	
	private RetrieveHtmlPage settingUrl(String url) {
		return new RetrieveHtmlPage(url); 
	}

	@Test
	public void retrieveHtmlPageSuccess() {
		RetrieveHtmlPage retrieveHtmlPage = settingUrl("https://en.wikipedia.org/wiki/Europe");
		final StringBuilder sb = retrieveHtmlPage.fetchHtmlFromUrl();
		assertThat(sb, notNullValue());
		assertThat(sb.length(),greaterThan(0));  
	}
	
	@Test(expected = RuntimeException.class)
	public void retrieveHtmlPageFailDueInexistentConnection() {
		RetrieveHtmlPage retrieveHtmlPage =  settingUrl("https://localhost");
		retrieveHtmlPage.fetchHtmlFromUrl();
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void retrieveHtmlPageFailDueIllegalUrl() {
		RetrieveHtmlPage retrieveHtmlPage =  settingUrl("xxx:\\//localhost");
		retrieveHtmlPage.fetchHtmlFromUrl();
		fail();
	}

}
