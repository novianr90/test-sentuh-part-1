package id.novian.testservicesentuhtech;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ServiceSendString  extends Service {

    private ServiceCallback serviceCallback;
    private final IBinder binder = new ServiceSendStringBinder();

    public class ServiceSendStringBinder extends Binder {
        ServiceSendString getService() {
            return  ServiceSendString.this;
        }
    }

    private WindowManager windowManager;
    private View view;

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        createServiceView();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void createServiceView() {

        WindowManager.LayoutParams params = getLayoutParams();

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {

            view = inflater.inflate(R.layout.service_widget, null);

            Button button = view.findViewById(R.id.btnSendString);

            button.setOnClickListener(v -> {
                if (serviceCallback != null) {
                    serviceCallback.onStringReceived("String from Service");
                }
            });

            windowManager.addView(view, params);
        }
    }

    @NotNull
    private static WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams params;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            );
        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }
        return params;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null) {
            windowManager.removeView(view);
        }
    }
}
