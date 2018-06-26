package com.sterilized.puyopuyo.puyo;

import java.awt.Container;

import com.sterilized.puyopuyo.Location;
import com.sterilized.puyopuyo.Main;

/**
 * handle two Puyo at once.
 */
public class PuyoPair implements Block {
    private Puyo first;
    private Puyo second;

    private Direction dir;

    /*- 
     *  d  r   u  l
     *  2      
     *  1  21  1  12
     *         2
     */
    enum Direction {
        DOWN(new Location(0, 1)), RIGHT(new Location(-1, 0)), UP(new Location(0, -1)), LEFT(new Location(1, 0));

        Location loc;

        Direction(Location loc) {
            this.loc = loc;
        }

        Direction rotate(boolean right) {
            switch (this) {
            case DOWN:
                return right ? RIGHT : LEFT;
            case RIGHT:
                return right ? UP : DOWN;
            case UP:
                return right ? LEFT : RIGHT;
            case LEFT:
                return right ? DOWN : UP;
            default:
                return null;
            }
        }

        Direction flip() {
            switch (this) {
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case LEFT:
                return RIGHT;
            default:
                return null;
            }
        }
    }

    public PuyoPair() {
        first = new Puyo(PuyoColor.random(), new Location(2, 11));
        second = new Puyo(PuyoColor.random(), new Location(2, 12));
        dir = Direction.DOWN;
    }

    public void rotate(boolean right) {
        Direction temp = dir;
        temp = temp.rotate(right);
        if (Puyo.findPuyo(Puyo.relativeLocation(first, temp.loc)) != null) { // is empty?
            if (Puyo.findPuyo(Puyo.relativeLocation(first, temp.flip().loc)) != null) {
                // both way are closed, just flip
                flip();
                return;
            } else { // closed in direction, pushed away from obstacle
                dir = temp;
                first.setLocation(Puyo.relativeLocation(first, temp.flip().loc));
                second.setLocation(Puyo.relativeLocation(first, temp.loc));
                return;
            }
        }
        dir = dir.rotate(right);
        second.setLocation(Puyo.relativeLocation(first, dir.loc));
    }

    private void flip() {
        dir = dir.flip();
        if (Puyo.findPuyo(Puyo.relativeLocation(first, dir.loc)) != null) {
            first.setLocation(Puyo.relativeLocation(first, dir.flip().loc));
        }
        second.setLocation(Puyo.relativeLocation(first, dir.loc));
    }

    @Override
    public boolean canMoveLeft() {
        if (first.canMoveLeft() && second.canMoveLeft())
            return true;
        return false;
    }

    @Override
    public boolean canMoveRight() {
        if (first.canMoveRight() && second.canMoveRight())
            return true;
        return false;
    }

    public boolean canMoveDown() {
        if (!first.canMoveDown() || !second.canMoveDown())
            return false;
        return true;
    }

    @Override
    public void moveLeft() {
        if (!canMoveLeft())
            return;
        first.moveLeft();
        second.moveLeft();
    }

    @Override
    public void moveRight() {
        if (!canMoveRight())
            return;
        first.moveRight();
        second.moveRight();
    }

    @Override
    public boolean moveDown() {
        if (!canMoveDown())
            return false;
        first.moveDown();
        second.moveDown();
        return true;
    }

    @Override
    public void stack() {
        if (dir != Direction.UP) {
            if (first.canMoveDown()) {
                first.fall();
            }
            first.stack();
        }

        if (second.canMoveDown()) {
            second.fall();
        }
        second.stack();

        if (dir == Direction.UP) {
            if (first.canMoveDown()) {
                first.fall();
            }
            first.stack();
        }

        if (first.getBelong().popable() || second.getBelong().popable()) {
            PuyoGroup.popAll();
        }

        Main.panel.repaint();
    }

    public void addInto(Container container) {
        container.add(first);
        container.add(second);
    }

}
