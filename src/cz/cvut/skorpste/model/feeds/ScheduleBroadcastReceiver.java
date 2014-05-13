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
public class ScheduleBroadcastReceiver extends BroadcastReceiver {

    public static final String SCHEDULE ="cz.cvut.skorpste.rafr.SCHEDULE";

    private static final long DOWNLOAD_INTERVAL = AlarmManager.INTERVAL_HOUR;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ScheduleBroadcastReceiver", "Setting repeating alarm");
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent feedIntent = new Intent(AlarmBroadcastReceiver.RUN);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, feedIntent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime()+1000,
                DOWNLOAD_INTERVAL, pendingIntent);
    }
}