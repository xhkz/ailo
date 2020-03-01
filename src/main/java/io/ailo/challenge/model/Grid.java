package io.ailo.challenge.model;

import lombok.Data;

@Data
public class Grid {
    private int xMax;
    private int yMax;

    public Grid(int size) {
        this.xMax = size - 1;
        this.yMax = size - 1;
    }
}
