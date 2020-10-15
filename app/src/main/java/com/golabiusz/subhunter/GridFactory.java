package com.golabiusz.subhunter;

public class GridFactory
{
    private static final int DEFAULT_GRID_WIDTH = 40;

    public Grid createGrid(int screenWidth, int screenHeight)
    {
        return createGrid(screenWidth, screenHeight, DEFAULT_GRID_WIDTH);
    }

    public Grid createGrid(int screenWidth, int screenHeight, int gridWidth)
    {
        int blockSize = screenWidth / gridWidth;
        int gridHeight = screenHeight / blockSize;

        return new Grid(gridWidth, gridHeight, blockSize);
    }
}
