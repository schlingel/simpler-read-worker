package at.fundev.simpler.reader.worker.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.squareup.okhttp.OkHttpClient;

import at.fundev.simpler.reader.worker.ContentExtractor;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class JsoupContentExtractor implements ContentExtractor {
	private static final String[] VALID_TAG_NAMES = new String[] {
		"ul", "ol", "p", "img", "pre", "h1", "h2", "h3", "h4", "h5", "h6", "h7"
	};
	
	private OkHttpClient client;
	
	public JsoupContentExtractor() {
		this.client = new OkHttpClient();
	}
	
	@Override
	public String extractContent(FeedItem feed) throws SimplerReaderException {
		StringBuffer buf = new StringBuffer();
		HttpURLConnection conn = null;
		try {
			conn = client.open(new URL(feed.getUrl()));
			
			Document doc = Jsoup.parse(conn.getInputStream(), "UTF-8", feed.getUrl());
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
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		
		return buf.toString();
	}
	
	private Element findContentContainer(Document doc) {
		Elements paragraphs = doc.select("p");
		Element container = null;
		int childCount = 0;
		
		for(Element paragraph : paragraphs) {
			Element parParent = paragraph.parent();
			int parsCount = countParagraphChildren(parParent);
			
			if(parsCount > childCount) {
				container = parParent;
				childCount = parsCount;
			}
		}
		
		return container;
	}
	
	private int countParagraphChildren(Element container) {
		int count = 0;
		
		for(Element cont : container.children()) {
			if(cont.nodeName().toLowerCase().equals("p")) {
				count++;
			}
		}
		
		return count;
	}
}
