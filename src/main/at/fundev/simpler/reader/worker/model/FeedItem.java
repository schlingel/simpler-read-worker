package at.fundev.simpler.reader.worker.model;

public class FeedItem {
	private String name;
	private String url;
	
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
}
