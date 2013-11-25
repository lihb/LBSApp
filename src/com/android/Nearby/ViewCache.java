package com.android.Nearby;

import com.readystatesoftware.mapviewballoons.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewCache {

	    private View baseView;
	    private TextView nameView;
	    private TextView textView;
	    private ImageView imageView;

	    public ViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public TextView getTextView() {
	        if (textView == null) {
	            textView = (TextView) baseView.findViewById(R.id.text);
	        }
	        return textView;
	    }

	    public TextView getNameView() {
	        if (nameView == null) {
	            nameView = (TextView) baseView.findViewById(R.id.name);
	        }
	        return nameView;
	    }

	    public ImageView getImageView() {
	        if (imageView == null) {
	            imageView = (ImageView) baseView.findViewById(R.id.image);
	        }
	        return imageView;
	    }

}
