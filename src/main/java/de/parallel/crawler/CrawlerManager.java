package de.parallel.crawler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import de.parallel.crawler.model.Page;
import de.parallel.crawler.taks.URLProcessorTask;

public class CrawlerManager {

	private static CrawlerManager crawlerManager;
	
	private static Map<String, Page> storage;
	
	private static String FILE_NAME = "urllist.txt";

	private CrawlerManager() {
		
	}

	public static CrawlerManager getInstance() {
		if(crawlerManager == null) {
			crawlerManager = new CrawlerManager();
		}
		return crawlerManager;
	}
	
	private void exportFile(Collection<Page> pagesSummary) {
		List<String> content = pagesSummary
				                .parallelStream()
				                .map(Page::toString)
				                .map(s -> s+"\n")
				                .collect(Collectors.toList());
		try {
			Files.write(Paths.get(FILE_NAME), content.toString().getBytes(), StandardOpenOption.CREATE);
		} catch(IOException io) {
			io.printStackTrace();
		}
		
	}
	
	public void process() {
		final String initialSeed = "https://en.wikipedia.org/wiki/Europe";
		storage = new HashMap<>();
		storage.put(initialSeed, new Page(initialSeed));
		URLProcessorTask urlProcessor = new URLProcessorTask(storage);
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		commonPool.invoke(urlProcessor);
		exportFile(storage.values());
	}
	
	
}
