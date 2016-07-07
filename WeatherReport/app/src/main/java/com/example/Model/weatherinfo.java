package com.example.Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by intgawade20 on 7/4/2016.
 */
public class weatherinfo extends BaseObservable  {

    private String name;
    private String cinfo;
    private boolean loading;

    public weatherinfo(String name, String cinfo,boolean loading) {
        this.name = name;
        this.cinfo = cinfo;
        this.loading=loading;
    }

    @Bindable
    public String getName() {
        return this.name.toString();
    }

    @Bindable
    public String getCinfo() {
        return this.cinfo.toString();
    }

    @Bindable
    public boolean getLoading() {
        return this.loading;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.example.viewModel.BR.name);
    }

    public void setcInfo(String cinfo) {
        this.cinfo = cinfo;
        notifyPropertyChanged(com.example.viewModel.BR.cinfo);
    }
    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(com.example.viewModel.BR.loading);
    }
}
