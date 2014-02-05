package at.fundev.simpler.reader.worker;

import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public interface ContentExtractor {
	public String extractContent(FeedItem feed) throws SimplerReaderException;
}
