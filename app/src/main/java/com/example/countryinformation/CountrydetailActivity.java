package com.example.countryinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class CountrydetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail);
        Button b=(Button)this.findViewById(R.id.backButton);
        TextView capital=(TextView)this.findViewById(R.id.capitalName);
        TextView population=(TextView)this.findViewById(R.id.poNum);
        TextView area=(TextView)this.findViewById(R.id.AreaNum);
        ImageView map=(ImageView)this.findViewById(R.id.mapView);
        Country country=getIntent().getParcelableExtra("Country");
        capital.setText(country.getCapital());
        population.setText(country.getPopulation()+" Persons");
        area.setText(country.getAreaIntSqrKm()+" Km^2");
        ImageView imageCountry=(ImageView)this.findViewById(R.id.imageCountry);
        String imageCt=country.getImgUrl();
        Picasso.get().load(imageCt).into(imageCountry);
        Picasso.get().load(country.getMapUrl()).into(map);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });
    }
    public void backButton()
    {
        Intent t=new Intent(CountrydetailActivity.this,MainActivity.class);
        startActivity(t);
    }
}