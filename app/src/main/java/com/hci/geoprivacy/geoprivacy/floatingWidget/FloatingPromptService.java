
package com.hci.geoprivacy.geoprivacy.floatingWidget;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hci.geoprivacy.geoprivacy.MainActivity;
import com.hci.geoprivacy.geoprivacy.R;


/**
 * Created by maitraikansal on 11/18/17.
 */

public class FloatingPromptService extends Service {


    private WindowManager mWindowManager;
    private View mHeadView;

    public FloatingPromptService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the chat head layout we created
        mHeadView = LayoutInflater.from(this).inflate(R.layout.floatinghead_layout, null);

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the chat head position
        params.gravity = Gravity.CENTER | Gravity.RIGHT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //mWindowManager.getDefaultDisplay()
        mWindowManager.addView(mHeadView, params);

        //Set the close button.
        ImageView closeButton = (ImageView) mHeadView.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the service and remove the chat head from the window
                stopSelf();
            }
        });


        final ImageView chatHeadImage = (ImageView) mHeadView.findViewById(R.id.head_profile_iv);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        bounce.setRepeatCount(Animation.INFINITE);
        bounce.setRepeatMode(Animation.REVERSE);
        chatHeadImage.setAnimation(bounce);

        chatHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FloatingPromptService.this, MessageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                //close the service and remove the chat heads
                stopSelf();
            }
        });

      /*chatHeadImage.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //As we implemented on touch listener with ACTION_MOVE,
                        //we have to check if the previous action was ACTION_DOWN
                        //to identify if the user clicked the view or not.
                        if (lastAction == MotionEvent.ACTION_MOVE) {
                            //Open the chat conversation click.
                            Intent intent = new Intent(FloatingPromptService.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            //close the service and remove the chat heads
                            stopSelf();
                        }
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mHeadView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHeadView != null) mWindowManager.removeView(mHeadView);
    }
}

