package id.novian.testservicesentuhtech;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity implements ServiceCallback {

    private MaterialTextView tvShowString;

    private ServiceSendString serviceSendString;
    private boolean isBound = false;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton startServiceButton = findViewById(R.id.btnActivateService);
        tvShowString = findViewById(R.id.tvShowString);

        startServiceButton.setOnClickListener(v -> {
            bindToMyService();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindFromMyService();
    }

    @Override
    public void onStringReceived(String data) {
        tvShowString.setText(data);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceSendString.ServiceSendStringBinder binder = (ServiceSendString.ServiceSendStringBinder) service;
            serviceSendString = binder.getService();
            serviceSendString.setServiceCallback(MainActivity.this);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    private void bindToMyService() {
        serviceIntent = new Intent(this, ServiceSendString.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindFromMyService() {
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }
}