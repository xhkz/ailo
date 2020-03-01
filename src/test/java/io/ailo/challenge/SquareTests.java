package io.ailo.challenge;

import io.ailo.challenge.exception.InvalidInputException;
import io.ailo.challenge.model.Grid;
import io.ailo.challenge.model.Move;
import io.ailo.challenge.model.Square;
import io.ailo.challenge.model.SquareType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SquareTests {

    @Test
    void testBuildSquare() {
        Grid grid = new Grid(10);

        Square square = Square.builder()
                .grid(grid)
                .x(3)
                .y(4)
                .type(SquareType.ZOMBIE)
                .build();

        assertEquals(square.getX(), 3);
        assertEquals(square.getY(), 4);
        assertEquals(square.getType(), SquareType.ZOMBIE);
    }

    @Test
    void testBuildSquareFromCoordinate() {
        Grid grid = new Grid(10);
        String coordinate = "(2,3)";
        SquareType type = SquareType.ZOMBIE;

        Square square = Square.of(grid, coordinate, type);

        assertEquals(square.getX(), 2);
        assertEquals(square.getY(), 3);
    }

    @Test
    void testBuildSquareWithInvalidCoordinate() {
        Grid grid = new Grid(10);
        String coordinate = "(10,2)";
        SquareType type = SquareType.ZOMBIE;

        Exception exception = assertThrows(InvalidInputException.class,
                () -> Square.of(grid, coordinate, type)
        );

        String expectedMessage = "Invalid initial position.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testBuildSquareFromCoordinates() {
        Grid grid = new Grid(10);
        String coordinates = "(2,3)(1,2)(3,3)";
        SquareType type = SquareType.CREATURE;

        List<Square> squares = Square.ofList(grid, coordinates, type);

        assertEquals(squares.size(), 3);
    }

    @Test
    void testSquareMove() {
        Grid grid = new Grid(10);
        String coordinate = "(2,2)";
        SquareType type = SquareType.ZOMBIE;
        Square square = Square.of(grid, coordinate, type);

        Move.U.apply(square);
        assertEquals(square.getY(), 1);

        Move.D.apply(square);
        assertEquals(square.getY(), 2);

        Move.L.apply(square);
        assertEquals(square.getX(), 1);

        Move.R.apply(square);
        assertEquals(square.getX(), 2);
    }
}
