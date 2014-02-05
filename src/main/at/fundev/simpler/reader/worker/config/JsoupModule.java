package at.fundev.simpler.reader.worker.config;

import at.fundev.simpler.reader.worker.ContentExtractor;
import at.fundev.simpler.reader.worker.ExtractionProcessor;
import at.fundev.simpler.reader.worker.FeedReader;
import at.fundev.simpler.reader.worker.impl.JsoupContentExtractor;
import at.fundev.simpler.reader.worker.impl.JsoupFeedReader;

import com.google.inject.AbstractModule;

public class JsoupModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ContentExtractor.class).to(JsoupContentExtractor.class);
		bind(FeedReader.class).to(JsoupFeedReader.class);
	}
}
