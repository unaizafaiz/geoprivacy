package com.hci.geoprivacy.geoprivacy.floatingWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.hci.geoprivacy.geoprivacy.MainActivity;
import com.hci.geoprivacy.geoprivacy.R;

import java.util.Random;

/**
 * Created by maitraikansal on 11/14/17.
 */

public class PromptAppWidgetProvider extends AppWidgetProvider {


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.appwidget_provider_layout);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
           // views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }


        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                MainActivity.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.appwidget_provider_layout);
            Log.w("WidgetExample", String.valueOf(number));
            // Set the text
            //remoteViews.setTextViewText(R.id.button, String.valueOf(number));

            // Register an onClickListener
            Intent intent = new Intent(context, MainActivity.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
          //  remoteViews.setOnClickPendingIntent(R.id.button, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }
}
