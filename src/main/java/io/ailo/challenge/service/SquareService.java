package io.ailo.challenge.service;

import io.ailo.challenge.model.Grid;
import io.ailo.challenge.model.Move;
import io.ailo.challenge.model.Square;
import io.ailo.challenge.model.SquareType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SquareService {
    private static final int IDX_INPUT_SIZE = 0;
    private static final int IDX_INPUT_ZOMBIE = 1;
    private static final int IDX_INPUT_CREATURES = 2;
    private static final int IDX_INPUT_MOVE = 3;

    /**
     * Translate string of Move to array of Move
     *
     * @param moveStr string composite of U,D,L,R
     * @return array of Move
     */
    public List<Move> getMoves(String moveStr) {
        return Arrays.stream(moveStr.split(""))
                .map(Move::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * Get all squares from initial position to the end of movement
     *
     * @param start   initial zombie square
     * @param moveStr string composite of U,D,L,R
     * @return array of squares which zombie has passed
     */
    public List<Square> getMovePath(Square start, String moveStr) {
        List<Move> moves = getMoves(moveStr);
        List<Square> path = new ArrayList<>();
        path.add(start);

        Square current = start;
        for (Move move : moves) {
            current = current.to(move);
            path.add(current);
        }

        return path;
    }

    /**
     * Get final positions of all zombies including initial zombie and infected creatures.
     *
     * @param zombie    initial zombie square
     * @param creatures initial creature squares
     * @param moveStr   sequence of U,D,L,R
     * @return array of squares representing each zombie's position
     */
    public List<Square> getZombiePositions(Square zombie, List<Square> creatures, String moveStr) {
        List<Square> positions = new ArrayList<>();

        // first zombie movement
        List<Square> zombiePath = getMovePath(zombie, moveStr);

        // add last coordinate to final positions
        positions.add(zombiePath.stream().reduce((a, b) -> b).orElse(null));

        // infections of first zombie
        List<Square> infected = creatures.stream()
                .filter(c -> c.intersect(zombiePath))
                .collect(Collectors.toList());
        creatures.removeAll(infected);

        // loop infected creatures
        while (infected.size() > 0) {
            for (Square s : infected) {
                List<Square> infectedPath = getMovePath(s, moveStr);
                positions.add(infectedPath.stream().reduce((a, b) -> b).orElse(null));
                zombiePath.addAll(infectedPath);
            }

            infected = creatures.stream()
                    .filter(c -> c.intersect(zombiePath))
                    .collect(Collectors.toList());
            creatures.removeAll(infected);
        }

        return positions;
    }

    /**
     * Parse input txt file content and get final zombies' positions
     * <p>
     * {@link SquareService#getZombiePositions(Square, List, String)}
     *
     * @param input 4 lines of text contains:
     *              dimensions of the area (N)
     *              initial position of the zombie
     *              a list of positions of poor creatures
     *              a list of moves zombies will make
     */
    public List<Square> getZombiePositions(String input) {
        String[] lines = input.split("\n");

        if (lines.length < 4) {
            return Collections.emptyList();
        }

        int size = Integer.parseInt(lines[IDX_INPUT_SIZE]);
        String zombiePosition = lines[IDX_INPUT_ZOMBIE];
        String creaturePositions = lines[IDX_INPUT_CREATURES];
        String moveStr = lines[IDX_INPUT_MOVE];

        Grid grid = new Grid(size);
        Square zombie = Square.of(grid, zombiePosition, SquareType.ZOMBIE);
        List<Square> creatures = Square.ofList(grid, creaturePositions, SquareType.CREATURE);

        return getZombiePositions(zombie, creatures, moveStr);
    }

    /**
     * Parse input txt file content and return score and final positions in required format
     * <p>
     * {@link SquareService#getZombiePositions(String)}
     */
    public String getFinalOutput(String input) {
        List<Square> finalPositions = getZombiePositions(input);

        long score = finalPositions.stream()
                .filter(p -> p.getType().equals(SquareType.CREATURE))
                .count();

        String positions = finalPositions.stream()
                .map(Square::getPosStr)
                .collect(Collectors.joining(""));

        return String.format("zombies score: %s\nzombies positions:\n%s", (int) score, positions);
    }
}
