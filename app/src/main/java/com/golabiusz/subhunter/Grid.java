package com.golabiusz.subhunter;

public class Grid
{
    private int blockSize;
    private int width;
    private int height;

    Grid(int width, int height, int blockSize)
    {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
