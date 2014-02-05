package at.fundev.simpler.reader.worker.tools;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import at.fundev.simpler.reader.worker.ContentExtractor;
import at.fundev.simpler.reader.worker.config.WorkerModule;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class ContentExtractorApp {
	private ContentExtractor extractor;
	
	@Inject
	public ContentExtractorApp(ContentExtractor extractor) {
		this.extractor = extractor;
	}
	
	public String extract(String url) throws SimplerReaderException {
		FeedItem item = new FeedItem("", url);
		return extractor.extractContent(item);
	}
	
	public static void main(String[] args) throws SimplerReaderException {
		if(args.length < 1) {
			System.out.println("ContentExtractorApp <url>");
		} else {
			Injector injector = Guice.createInjector(new WorkerModule());
			ContentExtractorApp app = injector.getInstance(ContentExtractorApp.class);
			String url = args[0];
			System.out.println(app.extract(url));
		}
	}
}
