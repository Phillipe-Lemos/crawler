package de.parallel.crawler.taks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import de.parallel.crawler.model.Page;
import de.parallel.crawler.util.ParseHtmlPage;
import de.parallel.crawler.util.RetrieveHtmlPage;

/**
 * A RecursiveAction that is called by the ForkJoinPool class to split the job of 
 * extracting the links from a passed url as a parameter.
 * @author phillipe
 *
 */
public class URLProcessorTask extends RecursiveAction {

	private static final int THRESHOLD = 100;
	
	private static final long serialVersionUID = -5618382326736226237L;
	
	private Map<String, Page> storage;
	
	public URLProcessorTask(Map<String, Page> storage) {
		this.storage = storage;
	}
	
	private List<URLProcessorTask> createSubTaks() {
		final List<URLProcessorTask> subTaks = new ArrayList<>();
		final Map<String, Page> firstPart = new HashMap<>();
		final Map<String, Page> secondPart = new HashMap<>();
		int count = 1;
		for(Map.Entry<String, Page> entry : storage.entrySet()) {
			if((storage.size() / 2) > count++) {
				firstPart.put(entry.getKey(), entry.getValue());
			} else {
				secondPart.put(entry.getKey(), entry.getValue());
			}
		}
		subTaks.add(new URLProcessorTask(firstPart));
		subTaks.add(new URLProcessorTask(secondPart));
		return subTaks; 
	}

	private void processing(Map<String, Page> work) {
		Map<String, Page> newItems = new HashMap<>();
		storage.forEach((key, value)-> {
			if(!value.getIsVisited() && key.startsWith(Page.URL_PREFIX)) {
				newItems.putAll(new ParseHtmlPage(new RetrieveHtmlPage(key)).parseURLPage());
				value.markVisited();
				Page page = newItems.get(key);
				if(page != null) {
					page.incrementCounter();
				}
			}
			//ensures that won't be a infinity loop
			if(!key.startsWith(Page.URL_PREFIX)) {
				value.markVisited();
			}
		});
		// include only those are not there.
		newItems.forEach(storage::putIfAbsent);
		newItems.clear();
	}
	
	@Override
	protected void compute() {
		if (storage.size() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubTaks());
        } else {
           processing(storage);
        }
	}

}
