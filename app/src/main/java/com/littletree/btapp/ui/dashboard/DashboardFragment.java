package com.littletree.btapp.ui.dashboard;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.littletree.btapp.R;

import java.util.Set;

public class DashboardFragment extends Fragment {

    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    private AdapterView.OnItemClickListener mNewDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            Toast.makeText(getContext(), "请在蓝牙设置界面手动连接设备", Toast.LENGTH_SHORT).show();
            BluetoothLog.v("on Item Click");
        }
    };

    private void init(View root) {
        Button btn_dashboard_search = root.findViewById(R.id.button_scan);
        btn_dashboard_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothLog.v("btn_dashboard_search clicked");
                Toast.makeText(getContext(), R.string.scanning, Toast.LENGTH_LONG * 9).show();
                doScan();
            }
        });
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.device_name);
        ListView newDevicesListView = root.findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mNewDeviceClickListener);
        newDevicesListView.setVisibility(View.VISIBLE);
    }
//        private void init() {
//            Button scanButton = findViewById(R.id.button_scan);
//            scanButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Toast.makeText(DeviceList.this, R.string.scanning, Toast.LENGTH_LONG).show();
//                    doDiscovery();  //搜索蓝牙设备
//                }
//            });
//            mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
//            mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
//            //已配对蓝牙设备列表
//            ListView pairedListView = findViewById(R.id.paired_devices);
//            pairedListView.setAdapter(mPairedDevicesArrayAdapter);
//            pairedListView.setOnItemClickListener(mPaireDeviceClickListener);
//            //未配对蓝牙设备列表
//            ListView newDevicesListView = findViewById(R.id.new_devices);
//            newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
//            newDevicesListView.setOnItemClickListener(mNewDeviceClickListener);
//            //动态注册广播接收者
//            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(mReceiver, filter);
//            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//            registerReceiver(mReceiver, filter);
//            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
//            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
//            if (pairedDevices.size() > 0) {
//                findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
//                for (BluetoothDevice device : pairedDevices) {
//                    mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//                }
//            } else {
//                String noDevices = getResources().getText(R.string.none_paired).toString();
//                mPairedDevicesArrayAdapter.add(noDevices);
//            }
//        }
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BluetoothLog.v("on create view");
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(root);
        return root;
    }

    protected void doScan(){
        BluetoothClient mClient = new BluetoothClient(getActivity());
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 2)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(3000,2) // 再扫经典蓝牙3s,在实际工作中没用到经典蓝牙的扫描
                .build();

        mClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {//开始搜素
                BluetoothLog.v("search start");
                mNewDevicesArrayAdapter.add("test blue tooth");
            }

            @Override
            public void onDeviceFounded(SearchResult device) {//找到设备 可通过manufacture过滤
                Beacon beacon = new Beacon(device.scanRecord);
                BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));
            }

            @Override
            public void onSearchStopped() {//搜索停止
                BluetoothLog.v("search stopped");
            }

            @Override
            public void onSearchCanceled() {//搜索取消
                BluetoothLog.v("search canceled");
            }

        });
    }
}