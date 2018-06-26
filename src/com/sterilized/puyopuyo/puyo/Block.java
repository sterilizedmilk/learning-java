package com.sterilized.puyopuyo.puyo;

public interface Block {
    public boolean canMoveLeft();
    public boolean canMoveRight();
    public boolean canMoveDown();
    public void moveLeft();
    public void moveRight();
    public boolean moveDown();

    // move down to bottom.
    public default void fall() {
        while(moveDown());
    }
    
    // attach block
    public void stack();
}
