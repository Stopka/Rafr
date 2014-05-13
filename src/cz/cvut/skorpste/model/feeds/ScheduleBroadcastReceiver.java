package cz.cvut.skorpste.model.feeds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by stopka on 9.5.14.
 */
public class ScheduleBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent feedIntent = new Intent(context, FeedService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, feedIntent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                AlarmManager.INTERVAL_HOUR,
                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
    }
}