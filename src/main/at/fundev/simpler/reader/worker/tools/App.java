package at.fundev.simpler.reader.worker.tools;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import at.fundev.simpler.reader.worker.FeedReader;
import at.fundev.simpler.reader.worker.config.WorkerModule;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class App {
	private static Injector injector = Guice.createInjector(new WorkerModule());
	private static final int MAX_THREADS_COUNT = 10;
	private static final long TIMEOUT_IN_MINUTES = 10;
	
	private FeedReader reader;
	private ExecutorService threadPool;
	
	@Inject
	public App(FeedReader reader) {
		this.reader = reader;
		this.threadPool = Executors.newFixedThreadPool(MAX_THREADS_COUNT);
	}
	
	public void waitForTermination() {
		try {
			threadPool.shutdown();
			threadPool.awaitTermination(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO add error handling
			e.printStackTrace();
		}
	}
	
	public void extractFeedItems(String url) {
		try {
			Collection<FeedItem> items = reader.extractItems(url);
			
			for(FeedItem item : items) {
				ExtractorTask task = injector.getInstance(ExtractorTask.class);
				task.setItem(item);
				
				threadPool.execute(task);
			}
		} catch (SimplerReaderException e) {
			// TODO add error handling
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("USAGE: App <url-1> ... <url-n>");
		} else {
			App app = injector.getInstance(App.class);
			
			for(String url : args) {
				app.extractFeedItems(url);
			}
			
			app.waitForTermination();
		}
	}
}
