package com.example.dokuyonetim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gelensiparisler:
                startActivity(new Intent(getApplicationContext(), GelenSiparisler.class));
                break;

            case R.id.tamamlanansiparisler:
                startActivity(new Intent(getApplicationContext(), TamamlananSiparisler.class));
                break;

            case R.id.ürünler:
                startActivity(new Intent(getApplicationContext(), Urunler.class));
                break;

            case R.id.kullanıcılar:
                startActivity(new Intent(getApplicationContext(), Kullanicilar.class));
                break;

            case R.id.anasayfaicerikleri:
                startActivity(new Intent(getApplicationContext(), AnasayfaIcerikleri.class));
                break;

            case R.id.bildirim:
                startActivity(new Intent(getApplicationContext(), BildirimYonetimi.class));
                break;

        }
    }
}