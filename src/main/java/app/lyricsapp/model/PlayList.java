package app.lyricsapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayList implements Serializable {
    private static final long serialVersionUID = -9057488539381007502L;
    private ArrayList<Song> songs;
    private String name;
    public PlayList(String name){
        this.name = name;
        this.songs = new ArrayList<Song>();
    }
    public PlayList(String name, ArrayList<Song> songs){
        this.name = name;
        this.songs = songs;
    }
    public void append(Song song) throws AlreadyExistingElementException{


        if (isAlreadyInSongList(song)){
            //System.out.println("Already in this playlist");
            throw new AlreadyExistingElementException();
        }
        this.songs.add(song);
        System.out.println(song.getTitle() + " added to : " + this.getName());

    }
    public void remove(Song song){
        this.songs.remove(song);
    }

    public void remove(int index){
        this.songs.remove(index);
    }

    public Song getFirstSong(){
        return songs.get(0);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public String getName() {
        return name;
    }
    public int getSize(){
        return songs.size();
    }

    @Override
    public String toString() {
        return this.name;
    }
    public boolean isAlreadyInSongList(Song song){
        for (Song song1 : this.songs){
            if (song1.equals(song))
                return true;
        }
        return false;
    }

    //@Override
    public boolean equals(PlayList obj) {
        return this.name.equals(obj.getName());
    }

    public void setName(String name) {
        this.name = name;
    }

}



