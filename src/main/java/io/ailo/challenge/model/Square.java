package io.ailo.challenge.model;

import io.ailo.challenge.exception.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Square {
    private Grid grid;

    private int x;
    private int y;

    @EqualsAndHashCode.Exclude
    private SquareType type;

    /**
     * Generate Square object
     *
     * @param grid     the grid that contains this square
     * @param position initial coordinate
     * @param type     zombie or creature
     * @return Square object
     */
    public static Square of(Grid grid, String position, SquareType type) {
        String[] xy = position.split(",");
        if (xy.length < 2)
            throw new InvalidInputException("Invalid position string.");

        int x = Integer.parseInt(xy[0].replaceAll("\\D+", ""));
        int y = Integer.parseInt(xy[1].replaceAll("\\D+", ""));

        if (x > grid.getXMax() || y > grid.getYMax())
            throw new InvalidInputException("Invalid initial position.");

        return new Square(grid, x, y, type);
    }

    /**
     * Generate List of Square object
     *
     * @param grid      the grid that contains these squares
     * @param positions initial coordinates
     * @param type      zombie or creature
     * @return List of Square objects
     */
    public static List<Square> ofList(Grid grid, String positions, SquareType type) {
        String[] xys = positions.split("\\)");
        return Arrays.stream(xys)
                .map(pos -> Square.of(grid, pos, type))
                .collect(Collectors.toList());
    }

    /**
     * Generate new Square object after Action
     *
     * @param action implementation which changes square
     * @return new Square object after action applies
     */
    public Square to(IAction action) {
        Square square = new Square(grid, x, y, type);
        action.apply(square);
        return square;
    }

    public boolean match(Square square) {
        if (square == null)
            return false;

        return x == square.getX() && y == square.getY();
    }

    /**
     * Check if Square is in the path of zombie's movement
     *
     * @param path zombie's movement
     * @return square is infected or not
     */
    public boolean intersect(List<Square> path) {
        return path.stream().anyMatch(s -> s.match(this));
    }

    public String getPosStr() {
        return String.format("(%s,%s)", x, y);
    }
}
