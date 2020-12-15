package com.littletree.btapp.ui.dashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.littletree.btapp.R;

public class DashboardFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BluetoothLog.v("on create view");
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Button btn_dashboard_search = root.findViewById(R.id.button_scan);
        btn_dashboard_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothLog.v("btn_dashboard_search clicked");
            }
        });
        return root;
    }
}