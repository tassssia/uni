package com.example.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameView extends View {
    private Cell[][] cells;
    private Cell player, exit;
    private static final int ROWS = 10, COLS = 7;
    private float cellSize, horMargin, vertMargin;
    private static final float WALL_THICKNESS = 4;
    private Paint wallPaint, playerPaint, exitPaint;
    private SecureRandom random = new SecureRandom();


    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        wallPaint = new Paint();
        wallPaint.setColor(Color.WHITE);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        playerPaint = new Paint();
        playerPaint.setColor(Color.WHITE);

        exitPaint = new Paint();
        exitPaint.setColor(Color.DKGRAY);

        createMaze();
    }

    private void createMaze() {
        cells = new Cell[COLS][ROWS];
        Stack<Cell> stack = new Stack<>();
        Cell curr, next;

        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        player = cells[0][0];
        exit = cells[COLS - 1][ROWS - 1];

        curr = cells[0][0];
        curr.visited = true;
        do {
            next = getNeighbour(curr);
            if (next != null) {
                removeWall(curr, next);

                stack.push(curr);
                curr = next;
                curr.visited = true;
            } else {
                curr = stack.pop();
            }
        } while (!stack.empty());
    }

    private Cell getNeighbour(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            addControlled(cell.getNeighbour(dir), neighbours);
        }

        if (neighbours.isEmpty()) {
            return null;
        }
        int index = random.nextInt(neighbours.size());
        return neighbours.get(index);
    }
    private void addControlled(Cell toAdd, List<Cell> list) {
        if (toAdd != null && !toAdd.visited) {
            list.add(toAdd);
        }
    }

    private void removeWall(Cell curr, Cell next) {
        if(next.isNeighbourOf(curr, Direction.UP)) {
            curr.topWall = false;
            next.bottomWall = false;
        }
        else if(next.isNeighbourOf(curr, Direction.DOWN)) {
            curr.bottomWall = false;
            next.topWall = false;
        }
        else if(next.isNeighbourOf(curr, Direction.LEFT)) {
            curr.leftWall = false;
            next.rightWall = false;
        }
        else if(next.isNeighbourOf(curr, Direction.RIGHT)) {
            curr.rightWall = false;
            next.leftWall = false;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.drawColor(Color.RED);

        int width = getWidth();
        int height = getHeight();

        if (width / COLS < height / ROWS) {
            cellSize = width / (COLS + 1);
        }
        else {
            cellSize = height / (ROWS + 1);
        }

        horMargin = (width - COLS * cellSize) / 2;
        vertMargin = (height - ROWS * cellSize) / 2;

        canvas.translate(horMargin, vertMargin);
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (cells[x][y].topWall) {
                    canvas.drawLine(x * cellSize, y * cellSize,
                            (x + 1) * cellSize, y * cellSize, wallPaint);
                }
                if (cells[x][y].bottomWall) {
                    canvas.drawLine(x * cellSize, (y + 1) * cellSize,
                            (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
                }
                if (cells[x][y].leftWall) {
                    canvas.drawLine(x * cellSize, y * cellSize,
                            x * cellSize, (y + 1) * cellSize, wallPaint);
                }
                if (cells[x][y].rightWall) {
                    canvas.drawLine((x + 1) * cellSize, y * cellSize,
                            (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
                }
            }
        }

        float margin = cellSize / 10;
        canvas.drawRect(player.col * cellSize + margin, player.row * cellSize + margin,
                (player.col + 1) * cellSize - margin, (player.row + 1) * cellSize - margin,
                playerPaint);
        canvas.drawRect(exit.col * cellSize + margin, exit.row * cellSize + margin,
                (exit.col + 1) * cellSize - margin, (exit.row + 1) * cellSize - margin,
                exitPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();

            float playerCenterX = horMargin + (player.col + 0.5f) * cellSize;
            float playerCenterY = vertMargin + (player.row + 0.5f) * cellSize;

            float dx = x - playerCenterX, absDx = Math.abs(dx);
            float dy = y - playerCenterY, absDy = Math.abs(dy);

            if (absDx > cellSize || absDy > cellSize) {
                if (absDx > absDy) {
                    if (dx > 0) {
                        movePlayer(Direction.RIGHT);
                    }
                    else {
                        movePlayer(Direction.LEFT);
                    }
                }
                else {
                    if (dy > 0) {
                        movePlayer(Direction.DOWN);
                    }
                    else {
                        movePlayer(Direction.UP);
                    }
                }
            }

            return true;
        }

        return super.onTouchEvent(event);
    }
    private void movePlayer(Direction dir) {
        switch (dir) {
            case UP:
                if (!player.topWall) {
                    player = cells[player.col][player.row - 1];
                }
                break;
            case DOWN:
                if (!player.bottomWall) {
                    player = cells[player.col][player.row + 1];
                }
                break;
            case LEFT:
                if (!player.leftWall) {
                    player = cells[player.col - 1][player.row];
                }
                break;
            case RIGHT:
                if (!player.rightWall) {
                    player = cells[player.col + 1][player.row];
                }
                break;
        }

        checkExit();
        invalidate();
    }

    private void checkExit() {
        if (player == exit) {
            createMaze();
        }
    }

    private class Cell {
        boolean topWall = true, bottomWall = true, leftWall = true, rightWall = true;
        boolean visited = false;
        int col, row;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public Cell getNeighbour(Direction dir) {
            switch (dir) {
                case UP:
                    if (row > 0) {
                        return cells[col][row - 1];
                    }
                    break;
                case DOWN:
                    if (row < ROWS - 1) {
                        return cells[col][row + 1];
                    }
                    break;
                case LEFT:
                    if (col > 0) {
                        return cells[col - 1][row];
                    }
                    break;
                case RIGHT:
                    if (col < COLS - 1) {
                        return cells[col + 1][row];
                    }
                    break;
            }

            return null;
        }

        public boolean isNeighbourOf(Cell cell, Direction dir) {
            switch (dir) {
                case UP:
                    return cell.col == col && cell.row == row + 1;
                case DOWN:
                    return cell.col == col && cell.row == row - 1;
                case LEFT:
                    return cell.col == col + 1 && cell.row == row;
                case RIGHT:
                    return cell.col == col - 1 && cell.row == row;
            }

            return false;
        }
    }
}
