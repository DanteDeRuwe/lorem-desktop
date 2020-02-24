package domain;

public class MediaItem {

	private String url;

	public MediaItem(String url) {
		setUrl(url);
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String value) {
		this.url = value;
	}
	
}
