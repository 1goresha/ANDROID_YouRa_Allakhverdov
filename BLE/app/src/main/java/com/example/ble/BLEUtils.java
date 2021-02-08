package com.example.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BLEUtils{

    private BluetoothLeScanner scanner;
    private ScanSettings scanSettings = new ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
            .setReportDelay(0L)
            .build();


    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            // ...do whatever you want with this found device
            Log.d("scan", device.getName());
            Log.d("scan", "onScanResult");
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            Log.d("scan", "onBatchScanResults");
            // Ignore for now
        }

        @Override
        public void onScanFailed(int errorCode) {
            // Ignore for now
            Log.d("scan", "onScanFailed");
        }
    };


    public BLEUtils() {
//        this.bluetoothAdapter = bluetoothAdapter;
//        this.bluetoothLeScanner = bluetoothLeScanner;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        this.scanner = adapter.getBluetoothLeScanner();
    }

    public void doScan() {
        if (scanner != null) {
//            scanner.startScan(null, scanSettings, this);
            Log.d("scan", "scan started");
            String[] names = new String[]{"HONOR Band 5-997"};
            List<ScanFilter> filters = null;
            if(names != null) {
                filters = new ArrayList<>();
                for (String name : names) {
                    ScanFilter filter = new ScanFilter.Builder()
                            .setDeviceName(name)
                            .build();
                    filters.add(filter);
                }
            }
            scanner.startScan(scanCallback);
        } else {
            Log.d("scan", "could not get scanner object");
        }
    }


}
