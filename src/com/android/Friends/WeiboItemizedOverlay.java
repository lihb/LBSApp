/***
 * Copyright (c) 2011 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.android.Friends;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;
import com.readystatesoftware.mapviewballoons.BalloonOverlayView;

public class WeiboItemizedOverlay<Item extends OverlayItem> extends BalloonItemizedOverlay<WeiboOverlayItem> {

	private ArrayList<WeiboOverlayItem> m_overlays = new ArrayList<WeiboOverlayItem>();
	private Context c;
	
	public WeiboItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
	}

	public void addOverlay(WeiboOverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected WeiboOverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, WeiboOverlayItem item) {
		Toast.makeText(c, "onBalloonTap for overlay index " + index,
				Toast.LENGTH_LONG).show();
		return true;
	}

	@Override
	protected BalloonOverlayView<WeiboOverlayItem> createBalloonOverlayView() {
		// use our custom balloon view with our custom overlay item type:
		return new WeiboOverlayView<WeiboOverlayItem>(getMapView().getContext(), getBalloonBottomOffset());
	}

}
