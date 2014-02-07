package at.fundev.simpler.reader.worker.impl;

import at.fundev.simpler.reader.worker.Notifier;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class StdoutNotifier implements Notifier {
	@Override
	public void notify(String publicUrl, FeedItem item) {
		System.out.println(String.format("  ITEM: [%s] URL: [%s]", item.getName(), publicUrl));
	}
}
