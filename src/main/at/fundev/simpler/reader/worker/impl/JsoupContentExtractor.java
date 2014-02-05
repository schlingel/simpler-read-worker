package at.fundev.simpler.reader.worker.impl;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import at.fundev.simpler.reader.worker.ContentExtractor;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class JsoupContentExtractor implements ContentExtractor {
	private static final String[] VALID_TAG_NAMES = new String[] {
		"ul", "ol", "p", "img", "pre"
	};
	
	@Override
	public String extractContent(FeedItem feed) throws SimplerReaderException {
		StringBuffer buf = new StringBuffer();
		
		try {
			Document doc = Jsoup.connect(feed.getUrl()).get();
			Element contentContainer = findContentContainer(doc);
			
			for(Element child : contentContainer.children()) {
				String tagName = child.tagName();
				tagName = (tagName != null) ? tagName.toLowerCase() : null;
				
				if(Arrays.asList(VALID_TAG_NAMES).contains(tagName)) {
					buf.append(String.format("<%s>%s</%s>", tagName, child.html(), tagName));
				}
			}
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
		
		return buf.toString();
	}
	
	private Element findContentContainer(Document doc) {
		Elements paragraphs = doc.select("p");
		Element container = null;
		int childCount = 0;
		
		for(Element paragraph : paragraphs) {
			Elements silblingPars = paragraph.parent().select("p");
			
			if(silblingPars.size() > childCount) {
				container = paragraph.parent();
				childCount = silblingPars.size();
			}
		}
		
		return container;
	}
}
