package rms.graphaccelerometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by s16442932 on 22/03/2017.
 */

class MyChart extends View
{
    Paint paint;
    Canvas canvas;
    int x = 0;

    static int latestValue;
    static boolean latestFlag = false;

    static float val[];

    public MyChart (Context context)
    {
        super(context);

        paint = new Paint();
        paint.setColor (Color.BLACK);

        //g = new Random ();
        val = new float[144];

        for (int i = 0; i < 144; i ++)
        {
            val[i] = 0;
        }
    }

    @Override
    protected void onDraw (Canvas c)
    {
        int i;

        super.onDraw (c);

        c.drawPaint (paint);
        paint.setAntiAlias (true);

        c.drawColor(Color.BLACK);

        paint.setStyle (Paint.Style.STROKE);
        paint.setColor (Color.RED);

        paint.setStrokeWidth (2);

        canvas = c;

        // 1440 x 2560
        if (x < 143)
        {
            for (i = 0; i < x; i++)
            {
                canvas.drawLine(i * 10, 300 + val[i], i * 10 + 10, 300 + val[i + 1], paint);
                Log.i("ACC", i + ":" + val[i]);
            }
        }
        else
        {
            for (i = 0; i < 143; i ++)
            {
                val [i] = val [i + 1];
            }

            for (i = 0; i < 143; i ++)
            {
                canvas.drawLine(i* 10, 300 + val[i], (i + 1)* 10, 300 + val[i + 1], paint);
                Log.i("ACC", i + ":" + val[i]);
            }
        }
    }

    void tick ()
    {
        if (x < 143)
        {
            Log.i("ACC",  ">>>" + x);
            x = x + 1;
            if (latestFlag) val [x] = latestValue;
        }
        else
        if (latestFlag) val[143] = latestValue;

        invalidate();
    }
}
