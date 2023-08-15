package app.lyricsapp.model;

import java.io.IOException;
import java.io.Serializable;

public class Song implements Serializable {
    private static final long serialVersionUID = -2248003867374666695L;
    private String title;
    private Integer SongRank;
    private String URL;
    private String Artist;
    private String ArtistURL;
    private String lyric ;
    private String Checksum;
    private Integer LyrickID;
    private Integer TrackID;
    private String CoverUrl;
    private String Trackchecksum;





    public  Song(){
    }



    //setter

    public void setLyricSum(String sum) { this.Checksum = sum; }

    public void setLyricArtist(String artist) { this.Artist = artist; }

    public void setLyricRank( Integer rank) { this.SongRank = rank; }
    public void setLyricUrl(String url) { this.URL = url; }

    public void setLyrickID(Integer lyrickID) {this.LyrickID = lyrickID; }

    public void setLyric(String lyric) { this.lyric = lyric; }
    public void setTrackID(Integer trackID) { this.TrackID = trackID; }


    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setArtistURL(String url) { ArtistURL = url; }


    public void setSongRank(Integer songRank) {
        SongRank = songRank;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public void setChecksum(String checksum) {
        Checksum = checksum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverUrl(String coverUrl) {
        CoverUrl = coverUrl;
    }

    public void setTrackchecksum(String trackchecksum) {
        Trackchecksum = trackchecksum;
    }

    //getter
    public String getLyric() { return lyric; }


    public Integer getSongRank() {
        return SongRank;
    }

    public Integer getTrackID() {
        return TrackID;
    }

    public String getTitle() {
        return title;
    }

    public String getURL() {
        return URL;
    }

    public Integer getLyrickID() { return LyrickID; }

    public String getArtist() { return Artist; }

    public String getArtistURL() { return ArtistURL; }

    public String getChecksum() { return Checksum; }

    public String getCoverUrl(){
        return CoverUrl;

    }
    public String getCoverForce() throws IOException {
        //return ReadXMLFile.GetSong(GetData.getLyric(getLyrickID(),getChecksum())).getCoverUrl() ;
        return ReadXMLFile.GetCover(GetData.getLyric(getLyrickID(),getChecksum()));
    }


    public String toString() {

        return
                ColorConsole.ANSI_CYAN + title +ColorConsole.ANSI_RESET+" By " +
                ColorConsole.ANSI_GREEN+ Artist +ColorConsole.ANSI_RESET + " "+getSongRank()+"/10";


    }
    public String toString2() {

        return
                "|"+ColorConsole.ANSI_CYAN+title +ColorConsole.ANSI_RESET+  " By "+ColorConsole.ANSI_GREEN+ Artist+ColorConsole.ANSI_RESET+"|"+"\n---------------------------------------------"+
                "\n" + lyric ;



    }

    public String toString3() {

        return "|"+ title + " By "+ Artist+ "\n"  + lyric ;


    }


    public boolean equals(Song song) {
        //Song song = (Song) obj;
        return this.title.equals(song.getTitle())
                && this.Artist.equals(song.getArtist());
    }



}
