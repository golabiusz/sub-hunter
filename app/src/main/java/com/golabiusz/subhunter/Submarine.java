package com.golabiusz.subhunter;

public final class Submarine
{
    private int horizontalPosition;
    private int verticalPosition;

    Submarine(int horizontalPosition, int verticalPosition)
    {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
    }

    public int getHorizontalPosition()
    {
        return horizontalPosition;
    }

    public int getVerticalPosition()
    {
        return verticalPosition;
    }
}
