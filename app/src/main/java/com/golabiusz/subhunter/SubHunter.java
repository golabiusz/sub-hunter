package com.golabiusz.subhunter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class SubHunter extends Activity
{
    float horizontalTouched = -100;
    float verticalTouched = -100;
    boolean hit = false;
    int shotsTaken;
    int distanceFromSub;
    boolean debugging = false;

    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    Paint paint;
    Grid grid;
    Point size;
    Submarine sub;

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
        size = new Point();
        display.getSize(size);

        // Initialize all the objects ready for drawing
        blankBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();

        // Tell Android to set our drawing as the view for this app
        setContentView(gameView);

        // Initialize our grid based on the screen resolution
        GridFactory gridFactory = new GridFactory();
        grid = gridFactory.createGrid(size);

        Log.d("Debugging", "In onCreate");
        newGame();
        draw();
    }

    /*
        This code will execute when a new
        game needs to be started. It will
        happen when the app is first started
        and after the player wins a game.
     */
    private void newGame()
    {
        Random random = new Random();
        sub = new Submarine(random.nextInt(grid.getWidth()), random.nextInt(grid.getHeight()));
        shotsTaken = 0;

        Log.d("Debugging", "In newGame");
    }

    /*
       Here we will do all the drawing.
       The grid lines, the HUD,
       the touch indicator and the
       "BOOM" when a sub' is hit
    */
    private void draw()
    {
        gameView.setImageBitmap(blankBitmap);

        // Wipe the screen with a white color
        canvas.drawColor(Color.argb(255, 255, 255, 200));

        // Change the paint color to black
        paint.setColor(Color.argb(255, 50, 50, 150));

        // Draw the vertical lines of the grid
        for (int i = 1; i <= grid.getWidth(); i++) {
            canvas.drawLine(grid.getBlockSize() * i, 0, grid.getBlockSize() * i, size.y, paint);
        }

        // Draw the horizontal lines of the grid
        for (int i = 1; i <= grid.getHeight(); i++) {
            canvas.drawLine(0, grid.getBlockSize() * i, size.x, grid.getBlockSize() * i, paint);
        }

        // Draw the player's shot
        canvas.drawRect(
            horizontalTouched * grid.getBlockSize(),
            verticalTouched * grid.getBlockSize(),
            (horizontalTouched * grid.getBlockSize()) + grid.getBlockSize(),
            (verticalTouched * grid.getBlockSize()) + grid.getBlockSize(),
            paint
        );

        // Re-size the text appropriate for the
        // score and distance text
        paint.setTextSize(grid.getBlockSize() * 2);
        paint.setColor(Color.argb(255, 0, 0, 255));
        canvas.drawText(
                "Shots Taken: " + shotsTaken + "  Distance: " + distanceFromSub,
                grid.getBlockSize(),
                grid.getBlockSize() * 1.75f,
                paint
        );

        Log.d("Debugging", "In draw");
        if (debugging) {
            printDebuggingText();
        }
    }

    /*
        This part of the code will
        handle detecting that the player
        has tapped the screen
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        Log.d("Debugging", "In onTouchEvent");

        // Has the player removed their finger from the screen?
        if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            // Process the player's shot by passing the
            // coordinates of the player's finger to takeShot
            takeShot(motionEvent.getX(), motionEvent.getY());
        }

        return true;
    }

    /*
       The code here will execute when
       the player taps the screen It will
       calculate the distance from the sub'
       and determine a hit or miss
     */
    private void takeShot(float touchX, float touchY)
    {
        Log.d("Debugging", "In takeShot");

        // Add one to the shotsTaken variable
        ++shotsTaken;

        // Convert the float screen coordinates
        // into int grid coordinates
        horizontalTouched = (int) touchX / grid.getBlockSize();
        verticalTouched = (int) touchY / grid.getBlockSize();

        // Did the shot hit the sub?
        hit = horizontalTouched == sub.getHorizontalPosition() && verticalTouched == sub.getVerticalPosition();

        // How far away horizontally and vertically was the shot from the sub
        int horizontalGap = (int) horizontalTouched - sub.getHorizontalPosition();
        int verticalGap = (int) verticalTouched - sub.getVerticalPosition();

        // Use Pythagoras's theorem to get the distance travelled in a straight line
        distanceFromSub = (int) Math.sqrt(
                ((horizontalGap * horizontalGap) + (verticalGap * verticalGap))
        );

        if (hit) {
            // If there is a hit call boom
            boom();
        } else {
            // Otherwise call draw as usual
            draw();
        }
    }

    // This code says "BOOM!"
    private void boom()
    {
        gameView.setImageBitmap(blankBitmap);

        // Wipe the screen with a red color
        canvas.drawColor(Color.argb(255, 255, 0, 0));

        // Draw some huge white text
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(grid.getBlockSize() * 10);

        canvas.drawText("BOOM!", grid.getBlockSize() * 4, grid.getBlockSize() * 14, paint);

        // Draw some text to prompt restarting
        paint.setTextSize(grid.getBlockSize() * 2);
        canvas.drawText("Take a shot to start again", grid.getBlockSize() * 8, grid.getBlockSize() * 18, paint);

        // Start a new game
        newGame();
    }

    // This code prints the debugging text
    private void printDebuggingText()
    {
        Map<String, Object> debugData = new LinkedHashMap<>();

        debugData.put("numberHorizontalPixels", size.x);
        debugData.put("numberVerticalPixels", size.y);
        debugData.put("blockSize", grid.getBlockSize());
        debugData.put("gridWidth", grid.getWidth());
        debugData.put("gridHeight", grid.getHeight());
        debugData.put("horizontalTouched", horizontalTouched);
        debugData.put("verticalTouched", verticalTouched);
        debugData.put("subHorizontalPosition", sub.getHorizontalPosition());
        debugData.put("subVerticalPosition", sub.getVerticalPosition());
        debugData.put("hit", hit);
        debugData.put("shotsTaken", shotsTaken);
        debugData.put("debugging", debugging);

        paint.setTextSize(grid.getBlockSize());

        int i = 3;
        for (Map.Entry<String, Object> entry : debugData.entrySet()) {
            canvas.drawText(
                    entry.getKey() + " = " + entry.getValue(),
                    50,
                    grid.getBlockSize() * i,
                    paint
            );
            ++i;
        }
    }
}
