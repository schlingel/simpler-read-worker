package at.fundev.simpler.reader.worker;

import at.fundev.simpler.reader.worker.model.FeedItem;

public interface Notifier {
	public void notify(String publicUrl, FeedItem item);
}
