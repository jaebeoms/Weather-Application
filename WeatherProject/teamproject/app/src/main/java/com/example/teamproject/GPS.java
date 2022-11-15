package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;

public class GPS extends AppCompatActivity {
    private TextView txtResult;
    String aaa;
    double GPSLat;
    double GPSLon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        txtResult = (TextView) findViewById(R.id.tv_area);

        // 위치 관리자 객체 참조하기
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GPS.this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            // 가장최근 위치정보 가져오기
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                String provider = location.getProvider();
                 GPSLon = location.getLongitude();
                 GPSLat = location.getLatitude();
                double altitude = location.getAltitude();

                Geocoder geocoder = new Geocoder(GPS.this);

                List<Address> list = null;
                try {

                    list = geocoder.getFromLocation(GPSLat, GPSLon, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list != null) {
                    if (list.size() == 0) {
                        txtResult.setText("해당되는 주소 정보가 없습니다.");
                    } else {

                        String cut[] = list.get(0).toString().split(" ");
                        for (int i = 0; i < cut.length; i++) {
                            System.out.println("cut[" + i + "] : " + cut[i]);
                        }// cut[0] : Address[addressLines=[0:"대한민국
                        // cut[1] : 서울특별시  cut[2] : 송파구  cut[3] : 오금동
                        // cut[4] : cut[4] : 41-26"],feature=41-26,admin=null ~~~~

                        txtResult.setText(cut[2]);
                        aaa=cut[2];

                    }
                }
            }

            // 위치정보를 원하는 시간, 거리마다 갱신해준다.
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    2000,
                    10,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    2000,
                    10,
                    gpsLocationListener);
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // 위치 리스너는 위치정보를 전달할 때 호출되므로 onLocationChanged()메소드 안에 위지청보를 처리를 작업을 구현 해야합니다.
            String provider = location.getProvider();  // 위치정보
            double GPSLon = location.getLongitude(); // 위도
            double GPSLat = location.getLatitude(); // 경도
            double altitude = location.getAltitude(); // 고도

            Geocoder geocoder = new Geocoder(GPS.this);

            List<Address> list = null;
            try {

                list = geocoder.getFromLocation(GPSLat, GPSLon, 10);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (list != null) {
                if (list.size() == 0) {
                    txtResult.setText("해당되는 주소 정보가 없습니다.");
                } else {

                    String cut[] = list.get(0).toString().split(" ");
                    for (int i = 0; i < cut.length; i++) {
                        System.out.println("cut[" + i + "] : " + cut[i]);
                    }// cut[0] : Address[addressLines=[0:"대한민국
                    // cut[1] : 서울특별시  cut[2] : 송파구  cut[3] : 오금동
                    // cut[4] : cut[4] : 41-26"],feature=41-26,admin=null ~~~~

                    aaa=cut[2];
                }
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onProviderDisabled(String provider) {

        }
    };

    public double GPSLat() {
        return GPSLat;
    }

    public double GPSLon() {
        return GPSLon;
    }

    public String gpsArea() {
        return aaa;
    }

}
