package hu.bme.aut.amorg.examples.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import hu.bme.aut.amorg.examples.servicedemo.service.ServiceLocation;

public class LocationDashboardFragment extends Fragment {

    private TextView tvProviderValue;
    private TextView tvLatValue;
    private TextView tvLngValue;
    private TextView tvSpeedValue;
    private TextView tvAltValue;
    private TextView tvPosTimeValue;
    private TextView tvAccuranncy;
    private TextView tvBarrel;


    //private ServiceLocation.BinderServiceLocation binderServiceLocation = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_dashboard, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initField(R.id.fieldProvider, getActivity().getString(R.string.txt_provider));
        initField(R.id.fieldLat, getActivity().getString(R.string.txt_latitude));
        initField(R.id.fieldLng, getActivity()
                .getString(R.string.txt_longitude));
        initField(R.id.fieldSpeed, getActivity().getString(R.string.txt_speed));
        initField(R.id.fieldAlt, getActivity().getString(R.string.txt_alt));
        initField(R.id.fieldPosTime,
                getActivity().getString(R.string.txt_position_time));


        initField(R.id.fieldAcurancy, getActivity().getString(R.string.Accurancy));
        initField(R.id.fieldBarrel,  getActivity().getString(R.string.Barrel));
    }

    private void initField(int fieldId, String headText) {
        View viewField = getView().findViewById(fieldId);
        TextView tvHead = viewField.findViewById(R.id.tvHead);
        tvHead.setText(headText);

        switch (fieldId) {
            case R.id.fieldProvider:
                tvProviderValue = viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldLat:
                tvLatValue = viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldLng:
                tvLngValue = viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldSpeed:
                tvSpeedValue = viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldAlt:
                tvAltValue = viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldPosTime:
                tvPosTimeValue = viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldAcurancy:
                tvAccuranncy = viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldBarrel:
                tvBarrel = viewField.findViewById(R.id.tvValue);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver,
                new IntentFilter(ServiceLocation.BR_NEW_LOCATION));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mMessageReceiver);
        super.onPause();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location currentLocation = intent.getParcelableExtra(ServiceLocation.KEY_LOCATION);

            tvLatValue.setText("" + currentLocation.getLatitude());
            tvLngValue.setText("" + currentLocation.getLongitude());
            tvAltValue.setText("" + currentLocation.getAltitude());
            tvSpeedValue.setText("" + currentLocation.getSpeed());

            tvProviderValue.setText(currentLocation.getProvider());
            tvPosTimeValue.setText(new Date(currentLocation.getTime()).toString());

            tvAccuranncy.setText("" + currentLocation.getAccuracy());
            tvBarrel.setText("" + currentLocation.getBearing());


        }
    };
}