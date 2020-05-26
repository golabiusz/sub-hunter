package com.golabiusz.subhunter;

import android.graphics.Point;

public class GridFactory
{
    private static final int DEFAULT_GRID_WIDTH = 40;

    public Grid createGrid(Point size)
    {
        return createGrid(size, DEFAULT_GRID_WIDTH);
    }

    public Grid createGrid(Point size, int gridWidth)
    {
        int blockSize = size.x / gridWidth;
        int gridHeight = size.y / blockSize;

        return new Grid(gridWidth, gridHeight, blockSize);
    }
}
