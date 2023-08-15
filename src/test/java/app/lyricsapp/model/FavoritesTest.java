package app.lyricsapp.model;

import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class FavoritesTest {

    Song songTest1 = new Song();
    Song songTest2 = new Song();




    Favorites listOfFavoritesTest = new Favorites();

    public FavoritesTest() throws IOException {
    }

    @Test
    public void testAdd() throws IOException {
        //song title artist
        songTest1.setTitle("oui oui");
        songTest1.setArtist("Bassem");
        //test
        listOfFavoritesTest.add(songTest1);
        assertTrue(listOfFavoritesTest.get().contains(songTest1));
        assertFalse(listOfFavoritesTest.get().isEmpty());
        listOfFavoritesTest.remove(songTest1);
    }

    @Test
    public void testRemove() throws IOException {

        //song title artist
        songTest1.setTitle("oui oui");
        songTest1.setArtist("Bassem");
        //test add
        listOfFavoritesTest.add(songTest1);
        //test remove
        listOfFavoritesTest.remove(songTest1);
        assertFalse(listOfFavoritesTest.get().contains(songTest1));

    }

    @Test
    public void testGet() throws IOException {

        ArrayList<Song> listTest = new ArrayList<Song>();
        listTest.add(songTest1);
        listTest.add(songTest2);

        listOfFavoritesTest.add(songTest1);
        listOfFavoritesTest.add(songTest2);

        assertEquals(listOfFavoritesTest.get(),listTest);
    }

}
