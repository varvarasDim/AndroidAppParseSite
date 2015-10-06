package org.varvaras.dimitris;





import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;





import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 


public class ArticleScreen extends Activity {
    /** Called when the activity is first created. */
    
    ImageView mainArticleImage;
    TextView mainArticleTitle;
    TextView mainArticleText;
    TextView mainArticleDate;
    private ProgressDialog pd;
    Context context;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.articlescreen);
        context = this;
 
        //ImageView mainArticleImage = (ImageView) findViewById(R.id.);
        mainArticleTitle = (TextView) findViewById(R.id.item_article_title);
        mainArticleText = (TextView) findViewById(R.id.item_article);
        mainArticleDate = (TextView) findViewById(R.id.item_date);
        mainArticleImage = (ImageView) findViewById(R.id.image_view);
        
        Button btnBack = (Button) findViewById(R.id.back_button);
 
        Intent i = getIntent();
        // Receiving the Data
        String url = i.getStringExtra("url");
        String date = i.getStringExtra("date");
        Log.e("Second Screen", url + "." + date);
 
        // Displaying Received data
        
      
		

        mainArticleDate.setText(date);
        mainArticleDate.setTextColor(Color.BLACK);
        new DownloadContentTask().execute(url);
		
		

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                
                finish();
            }
        });
    }
    

    private class DownloadContentTask extends AsyncTask<String,Void,Content> 
    {
    	
    	protected void onPreExecute() {
			pd = new ProgressDialog(context);
			pd.setTitle("Loading Article...");
			pd.setMessage("Please Wait...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
    	
        protected Content doInBackground(String... urls) 
        {
            return getContentFromURL(urls[0]);
        }

        protected void onPostExecute(Content result) 
        {
        	mainArticleImage.setImageBitmap(result.bitmap);
        	mainArticleTitle.setText(result.articleTitle);
        	mainArticleTitle.setTextColor(Color.BLACK);
        	mainArticleTitle.setTypeface(null, Typeface.BOLD);
        	
    		mainArticleText.setText(result.articleText);
    		mainArticleText.setTextColor(Color.BLACK);
			if (pd!=null) {
				pd.dismiss();
			}
        	
        }

        
        private Content getContentFromURL(String src) {
            String imgurl = "";
            Content cont = new Content();
        	org.jsoup.nodes.Document article ;
    		try {
    			article = Jsoup.connect(src).get();
    	    	org.jsoup.select.Elements ItemBody = article.getElementsByClass("itemBody");
        		org.jsoup.select.Elements ItemIntroText = ItemBody.first().getElementsByClass("itemIntro");
        		org.jsoup.select.Elements ItemFullText = ItemBody.first().getElementsByClass("itemFullText");
        		org.jsoup.select.Elements ItemTitleText = ItemBody.first().getElementsByClass("itemTitle");
        		org.jsoup.select.Elements ItemImage = ItemBody.first().getElementsByClass("itemImage");
            
        		imgurl = ItemImage.select("img").first().absUrl("src");
        		
        	
            	
        		cont.articleTitle =ItemTitleText.first().text();
        		cont.articleText =ItemIntroText.first().text() + "\n\n" +ItemFullText.first().text();
        		cont.bitmap = getBitmapFromURL(imgurl);
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return cont;
    		

    		
    		
        }
        
        private Bitmap getBitmapFromURL(String src) {
            try {
                Log.e("src",src);
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                Log.e("Bitmap","returned");
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Exception",e.getMessage());
                return null;
            }
        }



    }
    
    

	private class Content
	{
		String articleTitle;
		String articleText;
		Bitmap bitmap;

	}
    
    
  
    
}

