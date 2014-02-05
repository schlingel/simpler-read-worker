package at.fundev.simpler.reader.worker;

import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public interface ExtractionProcessor {
	void process(FeedItem feed, String extractedContent) throws SimplerReaderException;
}
