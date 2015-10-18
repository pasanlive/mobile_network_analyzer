package com.pasanlive.network.analyzer.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.TelecomManager;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasanlive.network.analyzer.R;
import com.pasanlive.network.analyzer.utility.NetworkManagerUtility;
import java.util.List;

/**
 * @author Pasan Buddhika Weerathunga (me@pasanlive.com)
 */
public class LogViewFragment extends Fragment {
    private TextView logView;
    private ConnectivityManager connectivityManager;
    private TelephonyManager telephonyManager;

    private PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            dumpTelephonyManagerData();
        }

        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            dumpTelephonyManagerData();
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            dumpTelephonyManagerData();
        }

        @Override
        public void onCellLocationChanged(CellLocation location) {
            dumpTelephonyManagerData();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_view, container, false);
        logView = (TextView) rootView.findViewById(R.id.log_view);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        getActivity().registerReceiver(networkStateChangeBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        addLog("starting . . .");

        analyzeNetworkInfo();
        return rootView;
    }

    private void analyzeNetworkInfo() {
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (networkInfo != null) {
            addLog(networkInfo.getExtraInfo());
            addLog(networkInfo.getDetailedState().name());
            addLog(networkInfo.getReason());
            addLog(networkInfo.toString());
        } else {
            addLog("Mobile network not available.");
        }

        dumpTelephonyManagerData();
    }


    private void dumpTelephonyManagerData() {
        if (telephonyManager != null) {
            StringBuilder sb = new StringBuilder("Call state : " + telephonyManager.getCallState() + ", ");
            sb.append("Network Operator : " + telephonyManager.getNetworkOperator() + ", ");
            sb.append("Network Operator Name : " + telephonyManager.getNetworkOperatorName() + ", ");
            sb.append("Sim Operator : " + telephonyManager.getSimOperatorName() + ", ");
            sb.append("Network Type : " + NetworkManagerUtility.getNetworkTypeName(telephonyManager.getNetworkType()) + ",  ");
            sb.append("Network Class" + NetworkManagerUtility.getNetworkClass(telephonyManager.getNetworkType()));


            if (telephonyManager.getAllCellInfo() != null) {
                for (CellInfo cellInfo : telephonyManager.getAllCellInfo()) {
                    sb.append("\n" + cellInfo.toString());
                }
            }

            addLog(sb.toString());
            telephonyManager.listen(phoneStateListener, telephonyManager.getNetworkType());
        }
    }





    private void addLog(String logMessage) {
        if (logView != null) {
            logView.setText(logMessage + "\n" + logView.getText());

        }
    }

    BroadcastReceiver networkStateChangeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            analyzeNetworkInfo();
        }
    };
}
