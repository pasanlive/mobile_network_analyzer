package com.pasanlive.network.analyzer.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasanlive.network.analyzer.R;
import com.pasanlive.network.analyzer.utility.NetworkManagerUtility;

/**
 * @author Pasan Buddhika Weerathunga (me@pasanlive.com)
 */
public class NetworkInfoFragment extends Fragment{
    private TextView networkOperatorTextView;
    private TextView networkTypeTextView;
    private TextView networkClassTextView;

    private TelephonyManager telephonyManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_network_info, container, false);

        networkOperatorTextView = (TextView) rootView.findViewById(R.id.networkOperatorTextView);
        networkTypeTextView = (TextView) rootView.findViewById(R.id.networkTypeTextView);
        networkClassTextView = (TextView) rootView.findViewById(R.id.networkClassTextView);

        telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        updateUI();

        return rootView;
    }

    private void updateUI() {
        if (telephonyManager != null) {
            networkOperatorTextView.setText(telephonyManager.getNetworkOperatorName());
            networkTypeTextView.setText(NetworkManagerUtility.getNetworkTypeName(telephonyManager.getNetworkType()));
            networkClassTextView.setText(NetworkManagerUtility.getNetworkClass(telephonyManager.getNetworkType()));
        }
    }


}
