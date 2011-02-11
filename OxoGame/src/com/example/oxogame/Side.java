package com.example.oxogame;

public enum Side {
    OSIDE,
    XSIDE;
    
    boolean side = true;

    public boolean getSide() {
        return side;
    }

    public void setSide(boolean side) {
        this.side = side;
    }
}
