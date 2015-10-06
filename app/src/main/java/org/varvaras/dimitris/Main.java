package org.varvaras.dimitris;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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



import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;




public class Main extends Activity {
    /** Called when the activity is first created. */

    String url;
	Button bt1,bt5,bt2,bt3,bt4;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        

        
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String toasttext = "";
                
                switch(position)
                {
                	case 0:
                		url = "http://www.newsbomb.gr/"+"diethnh/";
                		toasttext = "global";
                		break;
                	case 1:
                		url = "http://www.newsbomb.gr/"+"politikh/";
                		toasttext = "politics";
                		break;
                	case 2:
                		url = "http://www.newsbomb.gr/"+"koinwnia/";
                		toasttext = "society";
                		break;
                	case 3:
                		url = "http://www.newsbomb.gr/"+"ethnika/";
                		toasttext = "national";
                		break;
                	case 4:
                		url = "http://www.newsbomb.gr/"+"sports/";
                		toasttext = "sports";
                		break;
                	case 5:
                		url = "http://www.newsbomb.gr/"+"media-agb/";
                		toasttext = "media";
                		break;
                	case 6:
                		url = "http://www.newsbomb.gr/"+"chrhma/";
                		toasttext = "money";
                		break;
                	case 7:
                		url = "http://www.newsbomb.gr/"+"anexhghta/";
                		toasttext = "xfiles";
                		break;
                	case 8:
                		url = "http://www.newsbomb.gr/"+"apokalypseis/";
                		toasttext = "discoveries";
                		break;
                	case 9:
                		url = "http://www.newsbomb.gr/"+"kypros/";
                		toasttext = "cyprus";
                		break;
                	case 10:
                		url = "http://www.newsbomb.gr/"+"politismos/";
                		toasttext = "civilization";
                		break;
                	case 11:
                		url = "http://www.newsbomb.gr/"+"ygeia";
                		toasttext = "health";
                		break;
                	case 12:
                		url = "http://www.newsbomb.gr/"+"plus/";
                		toasttext = "bomb plus";
                		break;
                	default:
                		url = "http://www.newsbomb.gr/"+"diethnh/";
                		toasttext = "no choice";
                			;

                
                
                
                }
                
    			Toast.makeText(Main.this, toasttext, Toast.LENGTH_SHORT).show();
                
                Intent topicsScreen 
                = new Intent(getApplicationContext(), TopicsScreen.class);
                //Sending data to another Activity
        		topicsScreen.putExtra("url", url);
        		startActivity(topicsScreen);
                
                
            }
        });

        
    }
    
   
    
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
                
                
                
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            

            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
        		R.drawable.earth,
        		R.drawable.politics,
        		R.drawable.society,
        		R.drawable.greece,
        		R.drawable.football,
        		R.drawable.television,
        		R.drawable.money,
        		R.drawable.alien, 
        		R.drawable.secret,
        		R.drawable.cyprus,
        		R.drawable.books,
        		R.drawable.apple,
        		R.drawable.bomb
        };
    }
}