package rms.graphaccelerometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final MyChart d = new MyChart (this);
        setContentView (d);

        Timer myTimer = new Timer();
        myTimer.schedule (new TimerTask()
        {
            @Override
            public void run()
            {
                f ();
            }

            private void f ()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        d.tick ();
                    }
                });
            }
        }, 0, 100);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // for the system's orientation sensor registered listeners
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // to stop the listener and save battery
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        // TODO Auto-generated method stub
        String s = String.format ("X:%07.4f\nY:%07.4f\nZ:%07.4f\nG:%07.4f", event.values[0], event.values[1], event.values[2], Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2]));
        Log.i("ACC", s);

        MyChart.latestFlag = true;
        MyChart.latestValue = (int)event.values[0]*10;
    }
}

