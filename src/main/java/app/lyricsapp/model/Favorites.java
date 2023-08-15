package app.lyricsapp.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Favorites implements SaveToFile <Favorites>{


    private static final long serialVersionUID = -8122525885887854733L;
    private ArrayList<Song> listOfFavorites;

    public Favorites()  {
        try {
            this.listOfFavorites = this.getFromFile("serializationFiles/fav").get();
        } catch (IOException e) {
            this.listOfFavorites = new ArrayList<>();
            try{
                this.writeIntoFile("serializationFiles/fav");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * Add a song to the favorites list
     */
    public  void add(Song song) throws IOException {

        if(SearchIn(listOfFavorites,song)){
            System.out.println("/!Already In favorites!/");
        }else {
            listOfFavorites.add(song);
            writeIntoFile("serializationFiles/fav");
            System.out.println("Song added");
        }
    }
    public String getFavoriteArtist(ArrayList<Song> list){

        Map<String, Integer> countMap = new HashMap<>();

        for (Song s : list) {
            if (countMap.containsKey(s.getArtist())) {
                countMap.put(s.getArtist(), countMap.get(s.getArtist()) + 1);
            } else {
                countMap.put(s.getArtist(), 1);
            }
        }

        int maxCount = 0;
        String maxElement = "";
        boolean multipleMaxElements = false;

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                maxElement = entry.getKey();
            } else if (count == maxCount) {
                multipleMaxElements = true;
            }
        }
        if (multipleMaxElements) {
            System.out.println("Favorite Artist: none");
            return "";
        } else {
            System.out.println("Favorite Artist: " + maxElement);
            return maxElement;
        }


    }


    public static boolean SearchIn(List<Song> songs, Song song){
        for (Song element:songs){
            if(element.getTitle().equals(song.getTitle())&& element.getArtist().equals(song.getArtist())){
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a song from the favorites list
     */
    public void remove(Song song) throws IOException {
        listOfFavorites.remove(song);
        writeIntoFile("serializationFiles/fav");
    }
    public void remove(int index) throws IOException {
        listOfFavorites.remove(index);
        writeIntoFile("serializationFiles/fav");
    }

    /**
     * Returns the list of favorites
     */
    public ArrayList<Song> get() {
        return listOfFavorites;
    }




    public void removeInSearch(Song song) throws IOException {
        for(int i=0;i<listOfFavorites.size();i++){

            if(listOfFavorites.get(i).getTitle().equals(song.getTitle())&& listOfFavorites.get(i).getArtist().equals(song.getArtist())){
                System.out.println("//////removed/////////");
                //System.out.println(listOfFavorites.get(i).getTitle());


                listOfFavorites.remove(i);
                for(Song element :listOfFavorites){
                    System.out.println(element.getTitle());
                }
                writeIntoFile("serializationFiles/fav");

                break;
            }
        }
    }

     public static boolean isFavorite(Song song){
        Favorites favorites = new Favorites();
        return (Favorites.SearchIn(favorites.get(),song));
    }
    public int getSize(){
        return listOfFavorites.size();
    }

}
