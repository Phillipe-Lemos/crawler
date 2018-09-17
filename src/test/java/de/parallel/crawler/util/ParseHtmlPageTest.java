package de.parallel.crawler.util;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.parallel.crawler.model.Page;

@RunWith(MockitoJUnitRunner.class)
public class ParseHtmlPageTest {

	@Mock
	private RetrieveHtmlPage fetcher;
	
	@InjectMocks
	private ParseHtmlPage parseHtmlPage;
	
	@Before
	public void setUp() {
		try {
			Field field = RetrieveHtmlPage.class.getDeclaredField("url");
			field.setAccessible(Boolean.TRUE);
			field.set(fetcher, "http://localthost/test");
		} catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	private StringBuilder buildMockHTML() {
		final StringBuilder sb = new StringBuilder();
		sb.append("<html><body><h1>Teste</h1><a href=\"https://en.wikipedia.org/wiki/Europe\" />");
		sb.append("<a href=\"https://en.wikipedia.org/wiki/Asia\" >Asia</a>");
		sb.append("</body></html>");
		return sb;
	}
	
	@Test
	public void parseURLPageSuccess() {
       when(fetcher.fetchHtmlFromUrl()).thenReturn(buildMockHTML());
       final Map<String, Page> pages =  parseHtmlPage.parseURLPage();
       assertThat(pages, notNullValue());
       assertThat(pages.values(), not(empty()));
       assertThat(pages.keySet(), not(empty()));
       assertThat(pages.get("https://en.wikipedia.org/wiki/Europe"), notNullValue());
       assertThat(pages.get("https://en.wikipedia.org/wiki/Asia"), notNullValue());
	}

}
