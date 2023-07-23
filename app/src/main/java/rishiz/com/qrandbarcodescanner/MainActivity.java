package rishiz.com.qrandbarcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import android.Manifest;

public class MainActivity extends AppCompatActivity {
private static final int CAMERA_REQUEST_PERMISSION=1;
private static final int SCANNING_REQUEST_CODE=2;
private Button btnScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v->{
            startScanningActivity();
        });

    }

    private void startScanningActivity() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_PERMISSION);
            }else{
                startActivityForResult(new Intent(MainActivity.this,ScanningActivity.class),SCANNING_REQUEST_CODE);
            }
        }else {
            startActivityForResult(new Intent(MainActivity.this,ScanningActivity.class),SCANNING_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_REQUEST_PERMISSION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startActivityForResult(new Intent(MainActivity.this,ScanningActivity.class),SCANNING_REQUEST_CODE);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SCANNING_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                btnScan.setText(data.getStringExtra("scanning_result"));
            }
        }
    }
}