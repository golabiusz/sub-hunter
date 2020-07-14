package com.golabiusz.subhunter;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SubHunterActivity extends Activity
{
    private ImageView gameView;
    private SubHunterGame game;

    /*
        Android runs this code just before
        the player sees the app.
        This makes it a good place to add
        the code for the one-time setup phase.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Get the current device's screen resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Tell Android to set our drawing as the view for this app
        gameView = new ImageView(this);
        setContentView(gameView);

        game = new SubHunterGame(size, gameView);
    }

    /*
        This part of the code will
        handle detecting that the player
        has tapped the screen
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        return game.onTouchEvent(motionEvent);
    }
}
