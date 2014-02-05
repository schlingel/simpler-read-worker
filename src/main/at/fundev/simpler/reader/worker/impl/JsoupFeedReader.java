package at.fundev.simpler.reader.worker.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.squareup.okhttp.OkHttpClient;

import at.fundev.simpler.reader.worker.FeedReader;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;
import at.fundev.simpler.reader.worker.model.FeedItem;

public class JsoupFeedReader implements FeedReader {
	private OkHttpClient client;
	
	public JsoupFeedReader() {
		client = new OkHttpClient();
	}
	
	@Override
	public Collection<FeedItem> extractItems(String feedUrl) throws SimplerReaderException {
		Collection<FeedItem> items = new ArrayList<>();
		try {
			HttpURLConnection conn = client.open(new URL(feedUrl));
			Document doc = Jsoup.parse(conn.getInputStream(), "UTF-8", feedUrl, Parser.xmlParser());
			
			for(Element link : doc.select("item")) {
				items.add(new FeedItem(link.select("title").text(), link.select("link").text()));
			}
		} catch (MalformedURLException e) {
			throw new SimplerReaderException(e);
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
		
		return items;
	}
}
