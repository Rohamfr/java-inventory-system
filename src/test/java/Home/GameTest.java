package Home;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void testConstructorAndGetters() {
        Game game = new Game(1, "Halo", "Xbox", "FPS", 2001);

        assertEquals(1, game.getId());
        assertEquals("Halo", game.getTitle());
        assertEquals("Xbox", game.getPlatform());
        assertEquals("FPS", game.getGenre());
        assertEquals(2001, game.getYear());
    }

    @Test
    void testSetters() {
        Game game = new Game(2, "Old Title", "PC", "RPG", 2010);

        game.setTitle("New Title");
        game.setPlatform("PlayStation");
        game.setGenre("Action");
        game.setYear(2020);

        assertEquals("New Title", game.getTitle());
        assertEquals("PlayStation", game.getPlatform());
        assertEquals("Action", game.getGenre());
        assertEquals(2020, game.getYear());
    }

    @Test
    void testToStringContainsKeyInfo() {
        Game game = new Game(3, "Witcher 3", "PC", "RPG", 2015);
        String str = game.toString();

        assertTrue(str.contains("3"));
        assertTrue(str.contains("Witcher 3"));
        assertTrue(str.contains("PC"));
        assertTrue(str.contains("RPG"));
        assertTrue(str.contains("2015"));
    }
}
