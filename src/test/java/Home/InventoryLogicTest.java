package Home;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryLogicTest {

    @Test
    void testDeleteGameFromList() {
        ArrayList<Game> games = new ArrayList<>();

        games.add(new Game(1, "Game 1", "PC", "RPG", 2010));
        games.add(new Game(2, "Game 2", "Xbox", "Action", 2015));

        games.remove(1); // delete second item

        assertEquals(1, games.size());
        assertEquals(1, games.get(0).getId());
        assertEquals("Game 1", games.get(0).getTitle());
    }
}
