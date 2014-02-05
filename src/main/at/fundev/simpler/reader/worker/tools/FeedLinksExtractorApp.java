package at.fundev.simpler.reader.worker.tools;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import at.fundev.simpler.reader.worker.FeedReader;
import at.fundev.simpler.reader.worker.config.JsoupModule;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class FeedLinksExtractorApp {
	private FeedReader reader;
	
	@Inject
	public FeedLinksExtractorApp(FeedReader reader) {
		this.reader = reader;
	}
	
	public void printUrls(String url) throws SimplerReaderException {
		for(FeedItem item : reader.extractItems(url)) {
			System.out.println("  [" + item.getUrl() + "]");
		}
	}
	
	public static void main(String[] args) throws SimplerReaderException {
		if(args.length < 1) {
			System.out.println("Usage: FeedLinksExtractor <rss-feed>");
		} else {
			Injector injector = Guice.createInjector(new JsoupModule());
			FeedLinksExtractorApp app = injector.getInstance(FeedLinksExtractorApp.class);
			String url = args[0];
			app.printUrls(url);
		}
	}
}
