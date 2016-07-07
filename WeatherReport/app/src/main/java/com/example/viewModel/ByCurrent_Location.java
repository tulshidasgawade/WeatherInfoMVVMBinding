package com.example.viewModel;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Model.OpenWeatherMapTask;
import com.example.Model.weatherinfo;
import com.example.viewModel.databinding.BycurrentlocationBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ByCurrent_Location extends Activity {
	
	private ProgressBar pb =null;
	private TextView currcity=null,Message=null;
	private LocationManager locationMangaer=null;
	 private MyLocationListener locationListener=null;
	private BycurrentlocationBinding mBinding;
	private weatherinfo weather_info;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Binding Instance for "bycurrentlocation.xml"
		mBinding= DataBindingUtil.setContentView(this,R.layout.bycurrentlocation);
		// Initialize weatherinfo class with null values
		weather_info =new weatherinfo("","",true);
		mBinding.setInfo(weather_info);

		 locationMangaer = (LocationManager) 
				  getSystemService(Context.LOCATION_SERVICE);
		 Toast.makeText(getApplicationContext(), "IN current location", Toast.LENGTH_SHORT).show();
		// Cheak Gps in ON Or OFF
		ContentResolver contentResolver = getBaseContext()
				  .getContentResolver();
				  
				@SuppressWarnings("deprecation")
				boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, 
				  LocationManager.GPS_PROVIDER);
				  if(gpsStatus)
				  {
					  locationListener = new MyLocationListener();
					 
					   locationMangaer.requestLocationUpdates(LocationManager
					   .GPS_PROVIDER, 5000, 10,locationListener);
					  
				  }
				  else
				  {
					  Toast.makeText(getApplicationContext(), "GPS IS OFF", Toast.LENGTH_LONG).show();
					 
				  }
	}

	public void ClearData(View v)
	{
		weather_info =new weatherinfo("","",true);
		if(mBinding!=null)
		{
			mBinding.setInfo(weather_info);
		}
	}
	public class MyLocationListener implements LocationListener {

		public  String longitude=null;
		public  String latitude=null;
		public  String cityName=null;
		@Override
		public void onLocationChanged(Location loc) {
			// TODO Auto-generated method stub
			//
			weather_info.setLoading(false);
		      // Get City Name
		                 
		      Geocoder gcd = new Geocoder(getBaseContext(), 
		   Locale.getDefault());             
		      List<Address>  addresses;  
		      try {  
		      addresses = gcd.getFromLocation(loc.getLatitude(), loc
		   .getLongitude(), 1);  
		      if (addresses.size() > 0)  
		         System.out.println(addresses.get(0).getLocality());  
		         cityName=addresses.get(0).getLocality();
				  weather_info.setName(cityName);
				  new OpenWeatherMapTask(
						  weather_info.getName(),weather_info
				  ).execute();
		        } catch (IOException e) {            
		        e.printStackTrace();  
		      } 

		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
		

	}

}
