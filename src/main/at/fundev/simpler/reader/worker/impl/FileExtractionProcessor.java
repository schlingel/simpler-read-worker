package at.fundev.simpler.reader.worker.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import at.fundev.simpler.reader.worker.ExtractionProcessor;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class FileExtractionProcessor implements ExtractionProcessor {
	private static final String TEMPLATE = "<html><head><title>%s</title></head><body data-origin=\"%s\">%s</body></html>";
	
	private File directory;
	
	public FileExtractionProcessor() {
		String homeDir = System.getProperty("user.home");
		homeDir += homeDir.endsWith(File.separator) ? "" : File.separator;
		homeDir += "simpler-reader";
		
		directory = new File(homeDir);
		if(!directory.exists()) {
			directory.mkdirs();
			// TODO: handle case if mkdirs failed
		}
		
	}

	@Override
	public void process(FeedItem feed, String extractedContent) throws SimplerReaderException {
		try {
			String name = UUID.randomUUID().toString();
			String content = String.format(TEMPLATE, feed.getName(), feed.getUrl(), extractedContent);
			File htmlFile = new File(directory, name + ".html");
			PrintWriter writer = new PrintWriter(htmlFile);
			writer.print(content);
			writer.close();
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}

}