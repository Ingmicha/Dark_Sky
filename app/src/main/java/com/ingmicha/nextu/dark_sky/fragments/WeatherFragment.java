package com.ingmicha.nextu.dark_sky.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ingmicha.nextu.dark_sky.R;
import com.ingmicha.nextu.dark_sky.controllers.WeatherController;
import com.ingmicha.nextu.dark_sky.utils.WeatherUtils;
import com.ingmicha.nextu.dark_sky.views.IViewWeather;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Alejandro Ura A DesarrolloApps on 5/4/18.
 */

public class WeatherFragment extends Fragment implements IViewWeather {

    private static String TAG = WeatherFragment.class.getName();

    private IViewWeather mIViewWeather;

    private Context mContext;

    private View mView;

    private TextView mTextViewTimeZone;

    private TextView mTextViewCurrentWeather;

    private TextView mTextViewCurrentDay;

    private TextView mTextViewDayOne;
    private TextView mTextViewDayTwo;
    private TextView mTextViewDayThird;
    private TextView mTextViewDayFour;
    private TextView mTextViewDayFive;

    private ImageView mImageViewCurrent;

    private ImageView mImageViewDayOne;
    private ImageView mImageViewDayTwo;
    private ImageView mImageViewDayThird;
    private ImageView mImageViewDayFour;
    private ImageView mImageViewDayFive;

    private TextView mTextViewDayTempOne;
    private TextView mTextViewDayTempTwo;
    private TextView mTextViewDayTempThird;
    private TextView mTextViewDayTempFour;
    private TextView mTextViewDayTempFive;

    private TextView mTextViewSpeed;

    private WeatherUtils weatherUtils;

    private TableLayout mTableLayoutDay;

    List<TextView> textViewsDay;

    List<TextView> textViewsTemp;

    List<ImageView> imageViewsIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_weather, container, false);

        mTextViewTimeZone = mView.findViewById(R.id.textView_city);
        mTextViewCurrentDay = mView.findViewById(R.id.textView_current_day);
        mTextViewCurrentWeather = mView.findViewById(R.id.textView_current_wheater);

        mTextViewDayOne = mView.findViewById(R.id.textView_day_one);
        mTextViewDayTwo = mView.findViewById(R.id.textView_day_two);
        mTextViewDayThird = mView.findViewById(R.id.textView_day_third);
        mTextViewDayFour = mView.findViewById(R.id.textView_day_four);
        mTextViewDayFive = mView.findViewById(R.id.textView_day_five);

        mTextViewDayTempOne = mView.findViewById(R.id.textView_day_one_temp);
        mTextViewDayTempTwo = mView.findViewById(R.id.textView_day_two_temp);
        mTextViewDayTempThird = mView.findViewById(R.id.textView_day_third_temp);
        mTextViewDayTempFour = mView.findViewById(R.id.textView_day_four_temp);
        mTextViewDayTempFive = mView.findViewById(R.id.textView_day_five_temp);

        mImageViewCurrent = mView.findViewById(R.id.imageView_current_icon);

        mImageViewDayOne = mView.findViewById(R.id.imageView_day_one);
        mImageViewDayTwo = mView.findViewById(R.id.imageView_day_two);
        mImageViewDayThird = mView.findViewById(R.id.imageView_day_third);
        mImageViewDayFour = mView.findViewById(R.id.imageView_day_four);
        mImageViewDayFive = mView.findViewById(R.id.imageView_day_five);

        mTextViewSpeed = mView.findViewById(R.id.textView_windSpeed);

        mTableLayoutDay = mView.findViewById(R.id.simpleTableLayout);

        mTableLayoutDay.setVisibility(View.GONE);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIViewWeather = this;
        mContext = getContext();
        weatherUtils = new WeatherUtils();

        textViewsDay = new ArrayList<>();
        textViewsDay.add(mTextViewDayOne);
        textViewsDay.add(mTextViewDayTwo);
        textViewsDay.add(mTextViewDayThird);
        textViewsDay.add(mTextViewDayFour);
        textViewsDay.add(mTextViewDayFive);

        textViewsTemp = new ArrayList<>();
        textViewsTemp.add(mTextViewDayTempOne);
        textViewsTemp.add(mTextViewDayTempTwo);
        textViewsTemp.add(mTextViewDayTempThird);
        textViewsTemp.add(mTextViewDayTempFour);
        textViewsTemp.add(mTextViewDayTempFive);

        imageViewsIcon = new ArrayList<>();
        imageViewsIcon.add(mImageViewDayOne);
        imageViewsIcon.add(mImageViewDayTwo);
        imageViewsIcon.add(mImageViewDayThird);
        imageViewsIcon.add(mImageViewDayFour);
        imageViewsIcon.add(mImageViewDayFive);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void getUserLocation(String log,String lat){
        new WeatherController().getWeatherSource(mIViewWeather,mContext,log,lat);
    }

    @Override
    public void showWeatherInfo(String s) {

        Log.d(TAG,s);

        JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();

        String temperature =
                jsonObject.get("currently").getAsJsonObject().get("temperature").getAsString()+"º";
        String speed =
                jsonObject.get("currently").getAsJsonObject().get("windSpeed").getAsString()+"K/H";

        mTextViewTimeZone.setText(jsonObject.get("timezone").getAsString());

        mTextViewSpeed.setText(speed);

        mTextViewCurrentDay.setText(weatherUtils.unixTimeStampToDate(
                jsonObject.get("currently").getAsJsonObject().get("time").getAsLong(),
                "EEEE, MMM dd"));

        mTextViewCurrentWeather.setText(temperature);

        mImageViewCurrent.setImageDrawable(getDrawableByIcon(
                jsonObject.get("currently").getAsJsonObject().get("icon").getAsString()
        ));

        mTextViewCurrentWeather.setCompoundDrawables(null,null,getDrawableByIcon(
                jsonObject.get("currently").getAsJsonObject().get("icon").getAsString()),null);

        JsonArray jsonDays = jsonObject.get("daily").getAsJsonObject().get("data").getAsJsonArray();

        int day = 0;

        for (TextView textView :
                textViewsDay) {
            textView.setText(weatherUtils.unixTimeStampToDate(
                    jsonDays.get(day).getAsJsonObject().get("time").getAsLong(),
                    "EEE"));
            day++;
        }

        int temp = 0;

        for (TextView textView :
                textViewsTemp) {
            String tempDay =
                    jsonDays.get(temp).getAsJsonObject().get("temperatureHigh").getAsString()+ "º";
            textView.setText(tempDay);
            temp++;
        }

        int icon = 0;

        for (ImageView imageView:
                imageViewsIcon){
            String iconDay =
                    jsonDays.get(icon).getAsJsonObject().get("icon").getAsString();
            imageView.setImageDrawable(getDrawableByIcon(iconDay));
            icon++;

        }

        mTableLayoutDay.setVisibility(View.VISIBLE);
    }

    public Drawable getDrawableByIcon(String icon){

        Drawable img = null;
        switch (icon){
            case "clear-day":
                img = getContext().getResources().getDrawable( R.drawable.ic_sun );
                break;
            case "clear-night":
                img = getContext().getResources().getDrawable( R.drawable.ic_moon );
                break;
            case "rain":
                img = getContext().getResources().getDrawable( R.drawable.ic_rain );
                break;
            case "snow":
                img = getContext().getResources().getDrawable( R.drawable.ic_cloud );
                break;
            case "wind":
                img = getContext().getResources().getDrawable( R.drawable.ic_wind );
                break;
            case "cloudy":
                img = getContext().getResources().getDrawable( R.drawable.ic_cloud );
                break;
            case "partly-cloudy-day":
                img = getContext().getResources().getDrawable( R.drawable.ic_cloudy );
                break;
            case "partly-cloudy-night":
                img = getContext().getResources().getDrawable( R.drawable.ic_cloudy );
                break;
        }

        return img;
    }

    @Override
    public void errorMessage() {
        Toast.makeText(getActivity(),"No se logro obtener informacion",Toast.LENGTH_SHORT).show();
    }

}
