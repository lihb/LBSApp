package com.android.Nearby;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.readystatesoftware.mapviewballoons.R;


public class Nearby extends Activity{
    /** Called when the activity is first created. */
	BufferedReader in = null;
	ListView mListView;
	List<ImageAndText> mWeibos=new ArrayList<ImageAndText>();
	ImageAndText wb;
	ImageAndTextListAdapter  adapter;
	List<Bitmap> bmps = new ArrayList<Bitmap>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby);
		mListView = (ListView) findViewById(R.id.myArrayList);
		Bundle bundle = null;
		bundle = this.getIntent().getExtras();
		double lat = bundle.getDouble("lat");
		double lon = bundle.getDouble("lon");
		adapter=new ImageAndTextListAdapter(this, getData(lat,lon), mListView);
		mListView.setAdapter(adapter);

    }
	private List<ImageAndText> getData(double lat,double lon) {
	      
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream inStream = new FileInputStream(new File("/data/bks/sinaweibo.bks"));
			//InputStream inStream = getApplicationContext().getResources().openRawResource(R.raw.sinaweibo);
			trustStore.load(inStream, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", socketFactory, 443);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			HttpGet request = null;
			long currentTime = new Date().getTime()/1000;
			Log.i("time", ""+currentTime);
	        String url = "https://api.weibo.com/2/place/nearby/users.json?lat="+lat+"&long="+lon+"&access_token=2.00QXGFeCpIfjsC2b4b84745e0FeOMX";
	        //+"&count=50"
			request = new HttpGet(url);
	        
	        HttpResponse response = httpClient.execute(request);
	        InputStream instream = response.getEntity().getContent(); 
	        String respstr=convertStreamToString(instream);
	        
	        JSONObject result = new JSONObject(respstr);
	        JSONArray usersArray = result.getJSONArray("users");
	        
	        int count=usersArray.length();
	        for(int i=0;i<count;i++){
	        	JSONObject user_j = usersArray.getJSONObject(i);
	        	String name = (String)user_j.get("name");
	        	String imgurl=(String)user_j.get("profile_image_url");
	        	JSONObject status = user_j.getJSONObject("status");
	        	String text = status.getString("text");
	        	String time = status.getString("created_at");
	        	String content_time =  text+"\n(发表于："+time+")";
	        	
	        	//wb = new ImageAndText("http://tp4.sinaimg.cn/2024614115/50/1300380062/0","name",(String)result.getJSONObject(i).get("text"));
	        	wb = new ImageAndText(imgurl,name,content_time);
	    		mWeibos.add(wb);
	        }
	        
	       
        }
        catch(Exception e){
        	String err = e.getMessage();
        	System.out.println(err);
        }
		return mWeibos;
	}
	
   
    private static String convertStreamToString(InputStream is) {  
    	/*. 
    	* To convert the InputStream to String we use the BufferedReader.readLine(). 
    	* method. We iterate until the BufferedReader return null which means. 
    	* there's no more data to read. Each line will appended to a StringBuilder. 
    	* and returned as String.. 
    	*/
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