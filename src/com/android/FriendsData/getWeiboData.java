package com.android.FriendsData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;


public class getWeiboData{
	
	public  List<Weibodata> getData() {
		Weibodata data = null;
		List<Weibodata> dataList = new ArrayList<Weibodata>();
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			//InputStream inStream = .getResources().openRawResource(R.raw.sinaweibo);
			//Context c = MyApplication.getInstance();
			//c.getApplicationContext().getResources().openRawResource(R.raw.class.)
			FileInputStream inStream = new FileInputStream(new File("/data/bks/sinaweibo.bks"));
			trustStore.load(inStream, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", socketFactory, 443);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			HttpGet request = null;
			request = new HttpGet("https://api.weibo.com/2/place/friends_timeline.json?type=1&access_token=2.00QXGFeCpIfjsC2b4b84745e0FeOMX");
	        
	        HttpResponse response = httpClient.execute(request);
	        InputStream instream = response.getEntity().getContent(); 
	        //InputSource is = new InputSource(instream);
	        //is.setEncoding("UTF-8");
	        String respstr=convertStreamToString(instream);
	        
	        JSONObject result = new JSONObject(respstr);
	        JSONArray statuesArray = result.getJSONArray("statuses");
	        
	        int count=statuesArray.length();
	        for(int i=0;i<count;i++){
	        	JSONObject user_j = statuesArray.getJSONObject(i);
	        	JSONObject user = user_j.getJSONObject("user");
	        	String name = (String)user.get("name");
	        	String imgurl=(String)user.get("profile_image_url");
	        	/*判断geo是jsonobject还是null，根据首字母来判断，如果首字母是'｛',则是jsonobject，否则geo是null*/
	        	String  geotmp = user_j.getString("geo");
	        	char[] strChar = geotmp.substring(0, 1).toCharArray();
	        	char   firstCh = strChar[0];
	        	GeoPoint point;
	        	if (firstCh == '{') { 
					JSONObject geo = user_j.getJSONObject("geo");
					JSONArray coords = geo.getJSONArray("coordinates"); 
					Double lat = coords.getDouble(0)*1E6;
					Double lon = coords.getDouble(1)*1E6;
				    point = new GeoPoint(lat.intValue(), lon.intValue());
				}else {
					int random = new Random().nextInt(1000);
					point = new GeoPoint((int)(23.138824*1E6+random),(int)(113.349624*1E6+random));
				}
	        	
	        	String text = (String)statuesArray.getJSONObject(i).get("text");
	        	String time =  (String)statuesArray.getJSONObject(i).get("created_at");
	        	String content_time = text+"\n(发表于："+time+")";
	        	data = new Weibodata(point, name, content_time, imgurl);
	        	dataList.add(data);
	        }
		}catch(Exception e){
        	String err = e.getMessage();
        	System.out.println(err);
        }
		return dataList;
	}


	private String convertStreamToString(InputStream is) {
		InputStreamReader isr =new InputStreamReader(is);
    	BufferedReader reader = new BufferedReader(isr);  
    	
    	StringBuilder sb = new StringBuilder();  
    	String line = null;  
    	try {  
    		while ((line = reader.readLine()) != null) {  
    			sb.append(line + "\n");  
    	    }
    	} 
    	catch (IOException e) {  
    	   e.printStackTrace();  
    	}
    	finally {  
    	   try {  
    		   is.close();  
    	   }
    	   catch (IOException e) {  
    		   e.printStackTrace();  
    	   }  
    	}
    	
    	return sb.toString();  
	}
}