package at.fundev.simpler.reader.worker;

import java.util.Collection;

import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public interface FeedReader {
	public Collection<FeedItem> extractItems(String feedUrl) throws SimplerReaderException;
}
