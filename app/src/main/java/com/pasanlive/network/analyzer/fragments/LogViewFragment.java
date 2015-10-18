package com.pasanlive.network.analyzer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasanlive.network.analyzer.R;

/**
 * @author Pasan Buddhika Weerathunga (me@pasanlive.com)
 */
public class LogViewFragment extends Fragment {
    private TextView logView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_view, container, false);
        logView = (TextView) rootView.findViewById(R.id.log_view);
        addLog("starting . . .");
        return rootView;
    }

    private void addLog(String logMessage) {
        if (logView != null) {
            logView.append("\n" + logMessage);
        }
    }
}
