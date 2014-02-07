package at.fundev.simpler.reader.worker.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

import at.fundev.simpler.reader.worker.ExtractionProcessor;
import at.fundev.simpler.reader.worker.FileHandler;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

import com.google.inject.Inject;

public class FileExtractionProcessor implements ExtractionProcessor {
	private static final String TEMPLATE = "<html><head><meta charset=\"%s\"><title>%s</title></head><body data-origin=\"%s\">%s</body></html>";

	private File directory;
	private FileHandler handler;

	@Inject
	public FileExtractionProcessor(FileHandler handler) {
		this.handler = handler;
	}

	public FileExtractionProcessor() {
		String homeDir = System.getProperty("user.home");
		homeDir += homeDir.endsWith(File.separator) ? "" : File.separator;
		homeDir += "simpler-reader";

		directory = new File(homeDir);
		if (!directory.exists()) {
			directory.mkdirs();
			// TODO: handle case if mkdirs failed
		}

	}

	@Override
	public String process(FeedItem feed, String extractedContent)
			throws SimplerReaderException {
		try {
			String name = UUID.randomUUID().toString();
			String encoding = feed.getEncoding();

			System.out.println("Encoding: " + encoding);
			String content = String.format(TEMPLATE, feed.getEncoding(),
					feed.getName(), feed.getUrl(), extractedContent);
			File htmlFile = new File(directory, name + ".html");

			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(htmlFile), encoding);
			writer.write(content);
			writer.close();

			String url = handler.handle(htmlFile);

			return url;
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}

}
