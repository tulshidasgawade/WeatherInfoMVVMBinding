package com.example.viewModel;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Model.OpenWeatherMapTask;
import com.example.Model.weatherinfo;
import com.example.viewModel.databinding.BycityBinding;


public class ByCity_Name extends Activity {

	EditText editTextCityName;
    Button btnByCityName;
    TextView textViewResult, textViewInfo;
    ProgressBar progressbar;
	private BycityBinding mBinding;
	private weatherinfo weather_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Binding Instance for "bycity.xml"
		mBinding= DataBindingUtil.setContentView(this,R.layout.bycity);
		// Initialize weatherinfo class with null values
		weather_info =new weatherinfo("","",true);
		mBinding.setInfo(weather_info);
	}
	// Handle On Button Click to Fetch ewather info from API call
	public void showinfo(View v)
	{
		Toast.makeText(this, "Wait...Fetching Weather Information of "+weather_info.getName(), Toast.LENGTH_LONG).show();
		new OpenWeatherMapTask(
				weather_info.getName(),weather_info
				).execute();
	}
	public void ClearData(View v)
	{
		weather_info =new weatherinfo("","",true);
		if(mBinding!=null)
		{
			mBinding.setInfo(weather_info);
		}
	}
	
}
