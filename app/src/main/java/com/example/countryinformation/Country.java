package com.example.countryinformation;
import android.view.*;
import android.os.*;
public class Country implements Parcelable {
    public String countryName;
    public String imgUrl;
    public String population;
    public String areaIntSqrKm;
    public String capital;
    public String mapUrl;
    protected Country(Parcel in) {
        countryName = in.readString();
        imgUrl = in.readString();
        population = in.readString();
        areaIntSqrKm = in.readString();
        capital = in.readString();
        mapUrl=in.readString();
    }
      public Country()
      {

      }
    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getCountryName()
    {
        return countryName;
    }
    public void setCountryName(String countryName)
    {
        this.countryName=countryName;
    }
    public String getImgUrl()
    {
        return imgUrl;
    }
    public String getMapUrl(){return mapUrl;}
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getPopulation()
    {
        return this.population;
    }
    public void setPopulation(String population)
    {
        this.population=population;
    }
    public String getAreaIntSqrKm()
    {
        return this.areaIntSqrKm;
    }
    public void setAreaIntSqrKm(String areaIntSqrKm)
    {
        this.areaIntSqrKm=areaIntSqrKm;
    }
    public String getCapital()
    {
        return capital;
    }
    public void setCapital(String capital)
    {
        this.capital=capital;
}
    public void setMapUrl(String mapUrl){this.mapUrl=mapUrl;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryName);
        dest.writeString(imgUrl);
        dest.writeString(population);
        dest.writeString(areaIntSqrKm);
        dest.writeString(capital);
        dest.writeString(mapUrl);
    }
}
