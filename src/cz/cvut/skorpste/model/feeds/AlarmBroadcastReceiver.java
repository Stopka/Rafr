package cz.cvut.skorpste.model.feeds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by stopka on 9.5.14.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String RUN ="cz.cvut.skorpste.rafr.RUN";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmBroadcastReceiver", "Alarm call");
        Intent serviceIntent = new Intent(context, FeedService.class);
        context.startService(serviceIntent);
    }
}