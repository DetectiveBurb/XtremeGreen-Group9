package com.wolkabout.hexiwear.activity;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolkabout.hexiwear.R;
import com.wolkabout.hexiwear.model.Characteristic;
import com.wolkabout.hexiwear.model.Mode;
import com.wolkabout.hexiwear.service.BluetoothService;
import com.wolkabout.hexiwear.util.Dialog;
import com.wolkabout.hexiwear.util.HexiwearDevices;
import com.wolkabout.hexiwear.view.Reading;
import com.wolkabout.hexiwear.view.SingleReading;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;

import java.util.Map;

public class LightSensorActivity extends AppCompatActivity implements ServiceConnection {

    private Mode mode = Mode.IDLE;
    private BluetoothService bluetoothService;
    private static final String TAG = ReadingsActivity.class.getSimpleName();

    @ViewById
    SingleReading readingLight;

    @ViewById
    SingleReading readingTemperature;

    @ViewById
    SingleReading readingHumidity;

    @ViewById
    SingleReading readingPressure;

    @ViewById
    LinearLayout readings;

    @Bean
    HexiwearDevices hexiwearDevices;

    @Bean
    Dialog dialog;

    @ViewById
    TextView connectionStatus;

    @Extra
    BluetoothDevice device;

    @ViewById
    TextView textView;

    @ViewById
    TextView textView2;

    @Receiver(actions = BluetoothService.DATA_AVAILABLE, local = true)
    protected void getSensors(Intent intent){
        final String uuid = intent.getStringExtra(BluetoothService.READING_TYPE);
        final String data = intent.getStringExtra(BluetoothService.STRING_DATA);

        if (data.isEmpty()) {
            return;
        }

        final Characteristic characteristic = Characteristic.byUuid(uuid);
        if (characteristic == null) {
            Log.w(TAG, "UUID " + uuid + " is unknown. Skipping.");
            return;
        }

        switch (characteristic) {
            case TEMPERATURE:
                readingTemperature.setValue(data);
                break;
            case HUMIDITY:
                readingHumidity.setValue(data);
                break;
            case PRESSURE:
                readingPressure.setValue(data);
                break;
            case LIGHT:
                readingLight.setValue(data);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
    }

    @Override
    public void onServiceDisconnected(final ComponentName name) {
        // Something terrible happened.
    }

    @Override
    public void onServiceConnected(final ComponentName name, final IBinder service) {
        final BluetoothService.ServiceBinder binder = (BluetoothService.ServiceBinder) service;
        bluetoothService = binder.getService();
        if (!bluetoothService.isConnected()) {
            bluetoothService.startReading(device);
        }
        final Mode mode = bluetoothService.getCurrentMode();
        if (mode != null) {
            onModeChanged(mode);
        }
    }

    @Receiver(actions = BluetoothService.MODE_CHANGED, local = true)
    void onModeChanged(@Receiver.Extra final Mode mode) {
        this.mode = mode;
        connectionStatus.setText(mode.getStringResource());

        if (mode == Mode.IDLE) {
            dialog.showInfo(R.string.readings_idle_mode, false);
        }

        setReadingVisibility(mode);
    }

    private void setReadingVisibility(final Mode mode) {
        final Map<String, Boolean> displayPreferences = hexiwearDevices.getDisplayPreferences(device.getAddress());
        for (int i = 0; i < readings.getChildCount(); i++) {
            final Reading reading = (Reading) readings.getChildAt(i);
            final Characteristic readingType = reading.getReadingType();
            final boolean readingEnabled = displayPreferences.get(readingType.name());
            reading.setVisibility(readingEnabled && mode.hasCharacteristic(readingType) ? View.VISIBLE : View.GONE);
        }
    }

}
