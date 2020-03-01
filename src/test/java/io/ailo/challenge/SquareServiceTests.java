package io.ailo.challenge;

import io.ailo.challenge.model.Grid;
import io.ailo.challenge.model.Move;
import io.ailo.challenge.model.Square;
import io.ailo.challenge.model.SquareType;
import io.ailo.challenge.service.SquareService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Test Square Service")
public class SquareServiceTests {

    @Resource
    private SquareService squareService;

    @Test
    void testGetMoves() {
        String moveStr = "UUDDLLRR";
        List<Move> moves = squareService.getMoves(moveStr);

        assertEquals(moves.size(), 8);
        assertEquals(moves.get(2), Move.D);
    }

    @Test
    void testGetMovesWithIllegalString() {
        String moveStr = "UUDDAABBLLRR";

        assertThrows(IllegalArgumentException.class,
                () -> squareService.getMoves(moveStr)
        );
    }

    @Test
    void testGetMovePath() {
        String moveStr = "UUDDLLRR";

        Grid grid = new Grid(10);
        Square square = new Square(grid, 5, 5, SquareType.ZOMBIE);
        List<Square> path = squareService.getMovePath(square, moveStr);

        assertEquals(path.size(), 9);

        assertEquals(path.get(0).getX(), 5);
        assertEquals(path.get(0).getY(), 5);

        assertEquals(path.get(1).getX(), 5);
        assertEquals(path.get(1).getY(), 4);
    }

    @Test
    void testGetZombiePositions() {
        String input = "4\n" +
                "(2,1)\n" +
                "(0,1)(1,2)(3,1)\n" +
                "DLUURR\n";

        List<Square> positions = squareService.getZombiePositions(input);

        assertEquals(positions.size(), 4);

        assertEquals(positions.get(0).getX(), 3);
        assertEquals(positions.get(0).getY(), 0);

        assertEquals(positions.get(1).getX(), 2);
        assertEquals(positions.get(1).getY(), 1);
    }
}
