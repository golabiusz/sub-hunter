package com.golabiusz.subhunter;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class SubHunterActivity extends Activity
{
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

        game = new SubHunterGame(this, size.x, size.y);
        setContentView(game);
    }
}
