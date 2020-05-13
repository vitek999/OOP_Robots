package robots;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point to(@NotNull Direction direction, int delta) {
        int newX = x;
        int newY = y;
        switch (direction) {
            case NORTH:
                newY -= delta;
                break;
            case SOUTH:
                newY += delta;
                break;
            case EAST:
                newX += delta;
                break;
            case WEST:
                newX -= delta;
                break;
        }
        return new Point(newX, newY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
