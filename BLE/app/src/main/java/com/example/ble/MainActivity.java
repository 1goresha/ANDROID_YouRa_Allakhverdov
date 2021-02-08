package com.example.ble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    BLEScanCallback bleScanCallback;

    final String TAG = "scan";
    List<ScanFilter> scanFilterList;
    final int SCAN_PERIOD = 10000;
    TextView textView;
    Spinner spinner;
    List<String> arrayList;
    ArrayAdapter arrayAdapter;
    HashSet<String> hashSetNames;

    // Device scan callback.
    private final ScanCallback scanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
//            super.onScanResult(callbackType, result);
            BluetoothDevice bluetoothDevice = result.getDevice();
            Log.d(TAG, "Scan Success");
            String deviceName = bluetoothDevice.getName();
            textView.append(deviceName + "|||");
            if (deviceName != null){
//                arrayList.add(deviceName);
                hashSetNames.add(deviceName);
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
//            super.onBatchScanResults(results);
            Log.d(TAG, "Scan Success Batch");
            arrayList.clear();
            BluetoothDevice bluetoothDevice;
            for (ScanResult scanResult : results){
                bluetoothDevice = scanResult.getDevice();
                arrayList.add(bluetoothDevice.getName());
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
//            super.onScanFailed(errorCode);
            Log.d(TAG, "Error Code: " + errorCode);
        }
    };

    ScanSettings scanSettings = new ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
            .setReportDelay(0L)
            .build();

    BluetoothLeScanner bluetoothLeScanner;
    private boolean mScanning;
    private Handler handler = new Handler();

    // Stops scanning after 10 seconds.

    private void doScan() {
        if (bluetoothLeScanner != null) {
            Log.d(TAG, "scan started");
            if (!mScanning) {
                // Stops scanning after a pre-defined scan period.
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScanning = false;
                        bluetoothLeScanner.stopScan(scanCallback);
                        arrayList.addAll(hashSetNames);
                    }
                }, SCAN_PERIOD);

                mScanning = true;
                arrayList.clear();
                bluetoothLeScanner.startScan(null, scanSettings, scanCallback);
            } else {
                mScanning = false;
                bluetoothLeScanner.stopScan(scanCallback);
            }
        } else {
            Log.d(TAG, "could not get scanner object");
        }
    }

    private void doScanWithNames() {
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.startScan(scanFilterList, scanSettings, scanCallback);
            Log.d(TAG, "scan started");
        }  else {
            Log.d(TAG, "could not get scanner object");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hashSetNames = new HashSet<>();
        bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        spinner = (Spinner) findViewById(R.id.spinner);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
        final int REQUEST_ENABLE_BT = 1;
//        bleScanCallback = new BLEScanCallback();
        textView = (TextView) findViewById(R.id.textView);

        ActivityCompat.requestPermissions(
                this,
                new String[]
                        {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 0);


        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        String[] names = new String[]{"HONOR Band 5-997"};
        setScanFilterList(names);
//        doScanWithNames();
        doScan();
    }

    public void setScanFilterList(String[] names) {
        if (names != null) {
            scanFilterList = new ArrayList<>();
            for (String name : names) {
                ScanFilter filter = new ScanFilter.Builder()
                        .setDeviceName(name)
                        .build();
                scanFilterList.add(filter);
            }
        }
    }

}
