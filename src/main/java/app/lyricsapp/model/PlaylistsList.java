package app.lyricsapp.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlaylistsList implements SaveToFile<PlaylistsList>{

    private static final long serialVersionUID = -1601595846575258450L;
    private ArrayList<PlayList> playlistsList;

    public PlaylistsList(){
        try {
            PlaylistsList play =  getFromFile("serializationFiles/Play");
            this.playlistsList =  play.get();
        } catch (IOException e) {
            this.playlistsList = new ArrayList<>();
            try{
                this.writeIntoFile("serializationFiles/Play");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //
    public void append(String name) throws IOException,AlreadyExistingElementException{
        if(isAlreadyInPlaylistsList(new PlayList(name))) throw new AlreadyExistingElementException();
        playlistsList.add(new PlayList(name));
        this.writeIntoFile("serializationFiles/Play");
    }
    /*public void append(PlayList playlist){
        this.playlistsList.add(playlist);
    }*/
    public void remove(PlayList playlist) throws IOException {
        this.playlistsList.remove(playlist);
        this.writeIntoFile("serializationFiles/Play");
    }
    public void remove(int index) throws IOException {
        this.playlistsList.remove(index);
        this.writeIntoFile("serializationFiles/Play");
    }
    public int getSize(){
        return this.playlistsList.size();
    }

    public ArrayList<PlayList> get() {
        return playlistsList;
    }
    public PlayList searchPlaylistByName(String name){
        for(PlayList element : playlistsList){
            if(Objects.equals(element.getName(), name)){
                return  element;

            }
        }
        return null;
    }
    public int getPlaylistIndex(PlayList playlist){
        int i = -1;
        for(i = 0; i < this.playlistsList.size(); i++){
            if(playlist.equals(playlistsList.get(i)))
                break;
        }
        return i;
    }
    public boolean isAlreadyInPlaylistsList(PlayList playList){
        for (PlayList playList1 : this.playlistsList){
            if (playList1.equals(playList))
                return true;
        }
        return false;
    }


}
