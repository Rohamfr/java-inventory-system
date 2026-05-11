package Home;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryPersistenceTest {

    private static final String TEST_FILE = "test_games.dat";

    @AfterEach
    void cleanUp() {
        File f = new File(TEST_FILE);
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    void testSaveAndLoadGames() throws Exception {
        ArrayList<Game> originalGames = new ArrayList<>();
        originalGames.add(new Game(1, "Halo", "Xbox", "FPS", 2001));
        originalGames.add(new Game(2, "God of War", "PlayStation", "Action", 2018));


        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TEST_FILE))) {
            out.writeObject(originalGames);
        }

        assertTrue(new File(TEST_FILE).exists());


        ArrayList<Game> loadedGames;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(TEST_FILE))) {
            loadedGames = (ArrayList<Game>) in.readObject();
        }

        assertNotNull(loadedGames);
        assertEquals(2, loadedGames.size());

        for (int i = 0; i < originalGames.size(); i++) {
            Game o = originalGames.get(i);
            Game l = loadedGames.get(i);

            assertEquals(o.getId(), l.getId());
            assertEquals(o.getTitle(), l.getTitle());
            assertEquals(o.getPlatform(), l.getPlatform());
            assertEquals(o.getGenre(), l.getGenre());
            assertEquals(o.getYear(), l.getYear());
        }
    }
}
