package com.android.Nearby;

public class ImageAndText {
	    private String imageUrl;
	    private String text;
	    private String name;
	    
	    public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public ImageAndText(String imageUrl, String name,String text) {
	        this.imageUrl = imageUrl;
	        this.text = text;
	        this.name= name;
	    }
	    public String getImageUrl() {
	        return imageUrl;
	    }
	    public String getText() {
	        return text;
	    }
}
