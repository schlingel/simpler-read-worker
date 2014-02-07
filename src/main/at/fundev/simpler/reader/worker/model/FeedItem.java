package at.fundev.simpler.reader.worker.model;

public class FeedItem {
	private String name;
	private String url;
	private String encoding;
	
	public FeedItem(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	
	public FeedItem() {
		this(null, null);
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
