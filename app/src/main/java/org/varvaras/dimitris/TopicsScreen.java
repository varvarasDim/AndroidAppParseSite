package org.varvaras.dimitris;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.varvaras.dimitris.Base64.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.jsoup.Jsoup;





public class TopicsScreen extends ListActivity {


	
	private ProgressDialog pd;
    Handler handler = new Handler(Looper.getMainLooper());
    Context context;
    ListView lv;

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listplaceholder);
        context = this;

        

        Intent ni = getIntent();
        // Receiving the Data
        String url = ni.getStringExtra("url");
        	new DownloadTopicsTask().execute(url);
        	
			
			final ListView lv = getListView();
			
			lv.setTextFilterEnabled(true);	
			
			lv.setOnItemClickListener(new OnItemClickListener()
			{
				
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					

					HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);	  

					Intent nextScreen = new Intent(getApplicationContext(), ArticleScreen.class);
					 
	                //Sending data to another Activity
	                nextScreen.putExtra("url", o.get("url"));
	                nextScreen.putExtra("date", o.get("date"));
	                Log.e("n", o.get("url")+"."+ o.get("date"));
	                startActivity(nextScreen);
	                
	                
	                
	                
					//Toast.makeText(Main.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_LONG).show(); 
			
				}
			});

        
    }
    
    
    

    
    
    
    
    private class DownloadTopicsTask extends AsyncTask<String,Void,ListAdapter> 
    {
    	
    	protected void onPreExecute() {
			pd = new ProgressDialog(context);
			pd.setTitle("Loading...");
			pd.setMessage("Please Wait...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
    	
        protected ListAdapter doInBackground(String... urls) 
        {
            return getContentFromURL(urls[0]);
        }

        protected void onPostExecute(ListAdapter result) 
        {
        	
        	setListAdapter(result);
        	
			if (pd!=null) {
				pd.dismiss();
			}
        	
        }

        
        private ListAdapter getContentFromURL(String src) {
        	
    		ArrayList<HashMap<String, String>> mylist1 = new ArrayList<HashMap<String, String>>();
    		try {
    			org.jsoup.select.Elements baseElement = parse(src);
    		
    			
    	    	for (org.jsoup.nodes.Element OneItemBody : baseElement) 
    	    	{
    	    		org.jsoup.select.Elements ItemIntroText = OneItemBody.getElementsByClass("catItemIntroText");
    	    		org.jsoup.select.Elements ItemDateCreated = OneItemBody.getElementsByClass("catItemDateCreated");
    	    		org.jsoup.select.Elements ItemTitle = OneItemBody.getElementsByClass("catItemTitle");

    	    		for (int i=0 ; i<ItemIntroText.size();i++)
    	    		//for (org.jsoup.nodes.Element OneItemIntroText : ItemIntroText)
    	    		{
    					HashMap<String, String> map1 = new HashMap<String, String>();	
    					map1.put("title", ItemTitle.get(i).text()); 
    					map1.put("subtitle", ItemIntroText.get(i).text());
    		        	map1.put("date", ItemDateCreated.get(i).text());
    		        	map1.put("url", "http://www.newsbomb.gr"+ItemTitle.get(i).select("a").first().attr("href"));
    		        	mylist1.add(map1);			

    	    		}
    	    	}
    			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
            ListAdapter adapter = new SimpleAdapter(context, mylist1 , R.layout.topicsscreen, 
                    new String[] { "title", "subtitle" , "date" }, 
                    new int[] { R.id.item_title,R.id.item_subtitle, R.id.item_date });
            
    		return adapter;
    		

    		
    		
        }
        
        
        public org.jsoup.select.Elements parse(String url) throws IOException{
        	
        	org.jsoup.nodes.Document newsbomb_diethnh = Jsoup.connect(url).get();
        	org.jsoup.select.Elements ItemBody = newsbomb_diethnh.getElementsByClass("catItemBody");
        	return ItemBody;
        }



    }
    
    
    
    
    
    
    }