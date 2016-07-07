package com.example.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.Model.weatherinfo;
import com.example.viewModel.databinding.HomescreenBinding;

public class Homeactivity extends Activity {
	
	Button bycityname,currentloc;
	private weatherinfo weather_info;
	private HomescreenBinding mBinding;
	Intent intent;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding= DataBindingUtil.setContentView(this,R.layout.homescreen);
		weather_info =new weatherinfo("","",true);
		mBinding.setInfo(weather_info);
	}
	public void BiCityName(View v)
	{
		intent = new Intent(Homeactivity.this, ByCity_Name.class);
		startActivity(intent);
	}
	public void ByCurrentLocation(View v)
	{
		intent = new Intent(Homeactivity.this, ByCurrent_Location.class);
		startActivity(intent);
	}

}
