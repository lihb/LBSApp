
package com.android.Friends;

import java.util.Iterator;
import java.util.List;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.FriendsData.Weibodata;
import com.android.FriendsData.getWeiboData;
import com.android.Nearby.Nearby;
import com.android.Nearby.NearbyActivity;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.readystatesoftware.mapviewballoons.R;

public class FriendsMap extends MapActivity {

	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	Drawable drawable2;
	WeiboItemizedOverlay<WeiboOverlayItem> itemizedOverlay;
	private static final int MENU_SET_SATELLITE = 1;
    private static final int MENU_SET_MAP = 2;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		mapOverlays = mapView.getOverlays();
		
		drawable = getResources().getDrawable(R.drawable.marker);
		itemizedOverlay = new WeiboItemizedOverlay<WeiboOverlayItem>(drawable, mapView);
		WeiboOverlayItem overlayItem = null;
		List<Weibodata> dataList = new getWeiboData().getData();
		Iterator<Weibodata> it = dataList.iterator();
		while(it.hasNext()){
			Weibodata data = it.next();
			GeoPoint point = data.point;
			String title = data.userName;
			String snippet = data.content;
			String imageURL = data.img_url;
			overlayItem = new WeiboOverlayItem(point, title, snippet, imageURL);
			itemizedOverlay.addOverlay(overlayItem);
			mapOverlays.add(itemizedOverlay);
			
		}
		
		GeoPoint point = new GeoPoint((int)(31.189891*1E6),(int)(121.592277*1E6));
		
		
		final MapController mc = mapView.getController();
		mc.animateTo(point);
		mc.setZoom(5);
		
    }
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	 	// TODO Auto-generated method stub
	 	super.onCreateOptionsMenu(menu);
	 	menu.add(0, FriendsMap.MENU_SET_MAP, 0, "Map").setIcon(android.R.drawable.ic_menu_mapmode);
	 	menu.add(0, FriendsMap.MENU_SET_SATELLITE, 0, "Satellite").setIcon(android.R.drawable.ic_menu_mapmode);
	 	return true;
	 }
	 
	 @Override
	 public boolean onMenuItemSelected(int featureId, MenuItem item) {
	 		// TODO Auto-generated method stub
	 	switch (item.getItemId()) {
	         case FriendsMap.MENU_SET_MAP:
	             this.mapView.setSatellite(false);
	             break;
	         case FriendsMap.MENU_SET_SATELLITE:
	             this.mapView.setSatellite(true);
	             break;
	 		}
	 		return super.onMenuItemSelected(featureId, item);
	 	}

}
