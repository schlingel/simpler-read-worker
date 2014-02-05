package at.fundev.simpler.reader.worker.tools;

import at.fundev.simpler.reader.worker.ContentExtractor;
import at.fundev.simpler.reader.worker.ExtractionProcessor;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

import com.google.inject.Inject;

public class ExtractorTask implements Runnable {
	private ExtractionProcessor processor;
	private ContentExtractor extractor;
	private FeedItem item;
	
	@Inject
	public ExtractorTask(ExtractionProcessor processor, ContentExtractor extractor) {
		this.processor = processor;
		this.extractor = extractor;
	}
	
	@Override
	public void run() {
		try {
			String content = extractor.extractContent(item);
			processor.process(item, content);
		} catch(SimplerReaderException e) {
			// TODO: implement error handling
			e.printStackTrace();
		}
	}
	
	public void setItem(FeedItem item) {
		this.item = item;
	}
	
	public FeedItem getItem() {
		return this.item;
	}
}