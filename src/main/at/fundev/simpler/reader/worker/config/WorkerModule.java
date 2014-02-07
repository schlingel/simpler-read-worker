package at.fundev.simpler.reader.worker.config;

import at.fundev.simpler.reader.worker.Configuration;
import at.fundev.simpler.reader.worker.ContentExtractor;
import at.fundev.simpler.reader.worker.ExtractionProcessor;
import at.fundev.simpler.reader.worker.FeedReader;
import at.fundev.simpler.reader.worker.FileHandler;
import at.fundev.simpler.reader.worker.Notifier;
import at.fundev.simpler.reader.worker.impl.FileExtractionProcessor;
import at.fundev.simpler.reader.worker.impl.FtpFileHandler;
import at.fundev.simpler.reader.worker.impl.JsoupContentExtractor;
import at.fundev.simpler.reader.worker.impl.JsoupFeedReader;
import at.fundev.simpler.reader.worker.impl.PropertiesConfiguration;
import at.fundev.simpler.reader.worker.impl.StdoutNotifier;

import com.google.inject.AbstractModule;

public class WorkerModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ContentExtractor.class).to(JsoupContentExtractor.class);
		bind(FeedReader.class).to(JsoupFeedReader.class);
		bind(ExtractionProcessor.class).to(FileExtractionProcessor.class);
		bind(FileHandler.class).to(FtpFileHandler.class);
		bind(Configuration.class).to(PropertiesConfiguration.class);
		bind(Notifier.class).to(StdoutNotifier.class);
	}
}
