package app.lyricsapp;

import app.lyricsapp.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class LyricsAppCLI {
    public static void main(String[] args) throws IOException, AlreadyExistingElementException {
        Menu();
    }

    public static void Menu() throws IOException, AlreadyExistingElementException {
        System.out.println("\n");
        int userSelected;
        do{
            Scanner sc = new Scanner(System.in);
            userSelected =MenuData();
            switch (userSelected){
                case 0:
                    System. exit(0);
                    break;
                case 1:
                    ChooseMusic(GetData.searchLyric());
                    break;
                case 2:
                    ChooseMusic(GetData.searchLyricText());
                    break;
                case 3:
                    DisplayFavoriteList(sc);
                    break;
                case 4:
                    displayPlaylists(sc);
                default:
                    System.out.println("/! Enter a valid Number please !/");
                    break;
            }
        }
        while (userSelected>4 || userSelected<1);


    }
    public static int MenuData(){

        Scanner sc = new Scanner(System.in);
        System.out.println("********************************\n" +

                "*\t\t Lyrics Apps           *\n" +

                "********************************");

        System.out.println("---------------");
        System.out.println("0)"+ColorConsole.ANSI_RED+"Exit"+ColorConsole.ANSI_RESET);
        System.out.println("1)"+ColorConsole.ANSI_CYAN+"SearchSongs"+ColorConsole.ANSI_RESET);
        System.out.println("2)"+ColorConsole.ANSI_PURPLE+"SearchByLyric"+ColorConsole.ANSI_RESET);
        System.out.println("3)"+ColorConsole.ANSI_YELLOW+"Favorites Songs"+ColorConsole.ANSI_RESET);
        System.out.println("4)"+ColorConsole.ANSI_GREEN+"Playlist"+ColorConsole.ANSI_RESET);

        System.out.println("---------------");
        System.out.println("Enter a Number:");
        if(sc.hasNextInt()){
            return sc.nextInt();
        }else {
            return 9;
        }
    }
    public static void ChooseMusic(InputStream getData) throws IOException, AlreadyExistingElementException {
        Scanner sc = new Scanner(System.in);
        List<Song> songs = ReadXMLFile.GetListSongs(getData);
        System.out.println(" 1) popular song  or Any Number to continue");

        selectPopularSong(sc,songs);

        displayMore(sc,songs);





    }
    public  static  void selectPopularSong(Scanner sc,List<Song> songs) throws IOException, AlreadyExistingElementException {
        if(sc.hasNextInt()){
            int value= sc.nextInt();
            if(value==1){
                songs.removeIf(element -> element.getSongRank() < 7);
            }
            if(songs.isEmpty()){
                System.out.println("Song not found");
                Menu();
            }

        }else {
            System.out.println("Enter a valid Number");
            sc.nextLine();
            selectPopularSong(sc,songs);
        }
    }
    public static void DisplayFavoriteList(Scanner sc) throws IOException, AlreadyExistingElementException {

        Favorites favorites = new Favorites();
        int i=1;
        for(Song element : favorites.get()) {
            System.out.println("\n" +i+") "+ element.toString());
            i++;
        }

        if(favorites.get().isEmpty()){
            System.out.println("Favorite Empty");
        }
        favCommand(sc,favorites);
    }

    public static void DisplaySongLyrics(Scanner sc,List<Song> songs) throws IOException, AlreadyExistingElementException {

        if(sc.hasNextInt()){

            int value = sc.nextInt();

            if(value > songs.size() || value<1 ){
                System.out.println("retry a valid number...\n");
                DisplaySongLyrics(sc,songs);
            }

            Song SelectedSong =songs.get(value-1);

            System.out.println(ReadXMLFile.GetSong(GetData.getLyric(SelectedSong.getLyrickID(),SelectedSong.getChecksum())).toString2());
            System.out.println("---------------\n"+"0)"+ColorConsole.ANSI_RED+"Exit "+ ColorConsole.ANSI_RESET+
                    " | 1)" +ColorConsole.ANSI_YELLOW+"Add to favorite"+ColorConsole.ANSI_RESET+
                    " | 2)" +ColorConsole.ANSI_GREEN+"Add to playlist"+ColorConsole.ANSI_RESET+
                    " | 3)"+ColorConsole.ANSI_BLUE+" Menu"+ColorConsole.ANSI_RESET);
            ExitChoice(sc,SelectedSong);

        }else {
            sc.nextLine();
            System.out.println("retry a valid number..");
            DisplaySongLyrics(sc,songs);
        }
    }
    public static  void ExitChoice(Scanner sc,Song selectedSong) throws IOException, AlreadyExistingElementException {

        Favorites favorites = new Favorites();
        PlaylistsList playlistsList = new PlaylistsList();
        sc.nextLine();

        if (sc.hasNextInt()){
            switch (sc.nextInt()){
                case 0:
                    System. exit(0);
                    break;
                case 1:
                    favorites.add(selectedSong);
                    ExitChoice(sc,selectedSong);
                    break;
                case 2:
                    displayPlaylistsForSearch(sc,playlistsList,selectedSong);
                    System.out.println("---------------\n"+"0)"+ColorConsole.ANSI_RED+"Exit "+ ColorConsole.ANSI_RESET+
                            " | 1)" +ColorConsole.ANSI_YELLOW+"Add to favorite"+ColorConsole.ANSI_RESET+
                            " | 2)" +ColorConsole.ANSI_GREEN+"Add to playlist"+ColorConsole.ANSI_RESET+
                            " | 3)"+ColorConsole.ANSI_BLUE+" Menu"+ColorConsole.ANSI_RESET);
                    ExitChoice(sc,selectedSong);
                    ExitChoice(sc,selectedSong);
                    break;
                case 3:
                    Menu();
                    break;
                default:
                    System.out.println("retry a valid number..");
                    ExitChoice(sc,selectedSong);
                    break;
            }
        }else {
            System.out.println("retry a valid number");
            ExitChoice(sc,selectedSong);
        }
    }

    public static void favCommand(Scanner sc,Favorites favorites) throws IOException, AlreadyExistingElementException {
        System.out.println("---------------\n" + "Select a number : " + " | " + ColorConsole.ANSI_BLUE + "0)" + " for Menu " + ColorConsole.ANSI_RESET + " | " +
                ColorConsole.ANSI_CYAN + "1) to Select" + ColorConsole.ANSI_RESET +
                " | " + ColorConsole.ANSI_RED + "2) to " +
                "Delete" + ColorConsole.ANSI_RESET + " | \n---------------");


        favorites.getFavoriteArtist(favorites.get());

        if(sc.hasNextInt()){
            int value=sc.nextInt();
            if(  value<3){
                subFavMenu(value,favorites,sc);
            }
            favCommand(sc,favorites);
        }else {
            sc.nextLine();
            DisplayFavoriteList(sc);
        }
    }

    public static void subFavMenu(int value,Favorites favorites,Scanner sc) throws IOException, AlreadyExistingElementException {
        if(value==3){
            DisplayFavoriteList(sc);
        }

        if(value == 2){
            System.out.println( ColorConsole.ANSI_RED +"Delete mode  choose a number \n 0) to go back" + ColorConsole.ANSI_RESET);

            if(sc.hasNextInt()){

                int value3 = sc.nextInt();
                if(value3==0){
                    DisplayFavoriteList(sc);
                }
                if(value3<=favorites.get().size()){
                    favorites.remove(value3-1);
                    DisplayFavoriteList(sc);
                }
            }else {
                System.out.println("Enter a valid number");
                sc.next();
                subFavMenu(value,favorites,sc);
            }
        }
        else if(value==1){
            System.out.println( ColorConsole.ANSI_CYAN+"Select mode \n 0) to go back" + ColorConsole.ANSI_RESET);

            if(sc.hasNextInt()){
                int value3 = sc.nextInt();
                if(value3==0){
                    DisplayFavoriteList(sc);
                }
                if(value3<=favorites.get().size() ){
                    int element= value3-1;

                    System.out.println(ReadXMLFile.GetSong(GetData.searchLyricDirect(favorites.get().get(element).getArtist().replace(" ","%20"),favorites.get().get(element).getTitle().replace(" ","%20"))).toString2());
                    favCommand(sc,favorites);
                }

            }else {
                System.out.println("Enter a valid number");
                sc.next();
                subFavMenu(value,favorites,sc);
            }
        }
        else if(value==0){
            Menu();
        }

        System.out.println("enter a valid number");

        sc.nextLine();
        subFavMenu(value,favorites,sc);

    }


    public static void displayMore(Scanner sc,List<Song> songs) throws IOException, AlreadyExistingElementException {
        int i=1;
        int displayLenght = 0;
        while (true){
            if (songs.size() - displayLenght<5){

                for (int j = displayLenght; j<= songs.size()-1; j++){
                    System.out.println("\n" +i+") "+ songs.get(j).toString());
                    i++;
                }
                System.out.println("---------------\n"+"Select a song number");
                DisplaySongLyrics(sc,songs);

            }else {
                for(int j = displayLenght; j<=displayLenght+4; j++) {
                    System.out.println("\n" +i+") "+ songs.get(j).toString());
                    i++;
                }
                System.out.println(" 0) Display more  1) Select ");
                displayLenght= selectDisplayMore(sc,songs,displayLenght);

            }
        }
    }


    public static int selectDisplayMore(Scanner sc, List<Song> songs, int displayLength) throws IOException, AlreadyExistingElementException {

        if(sc.hasNextInt()) {
            int value = sc.nextInt();
            switch (value) {
                case 0:
                    return displayLength += 5;
                case 1:
                    System.out.println("---------------\n" + "Select a song number");
                    DisplaySongLyrics(sc, songs);
                    break;
                default:
                    System.out.println("---------------\n"+"retry a valid number");

                    selectDisplayMore(sc,songs,displayLength);
            }
        }else {
            System.out.println("---------------\n"+"retry a valid number");
            sc.nextLine();
            Menu();
        }
        return displayLength;
    }


    /** ------------------PLAYLIST------------------ */

    public static void displayPlaylists(Scanner sc) throws IOException, AlreadyExistingElementException {

        PlaylistsList listOfPlaylist = new PlaylistsList();
        int i = 1;
        for(PlayList element : listOfPlaylist.get()) {
            System.out.println("\n" + i + ") "+ element.toString());
            i++;
        }

        if(listOfPlaylist.get().isEmpty()){
            System.out.println("You don't have any playlists");
        }
        commandPlaylist(sc,listOfPlaylist);
    }

    public static void displayPlaylistsForSearch(Scanner sc, PlaylistsList listOfPlaylist, Song song) throws IOException, AlreadyExistingElementException {

        int i = 1;
        for(PlayList element : listOfPlaylist.get()) {
            System.out.println("\n" + i + ") "+ element.toString());
            i++;
        }

        System.out.println("Choose the playlist you want to add on");
        int choosenPlaylist = sc.nextInt()-1;
        try {
            listOfPlaylist.get().get(choosenPlaylist).append(song);
        }catch (AlreadyExistingElementException e){

        }


    }

    public static void commandPlaylist(Scanner sc,PlaylistsList listOfPLaylist) throws IOException, AlreadyExistingElementException {
        System.out.println("---------------\n"
                + "Select a number : " + " | "
                + ColorConsole.ANSI_BLUE + "0)" + " For menu " + ColorConsole.ANSI_RESET
                + " | " + ColorConsole.ANSI_GREEN + "1) To add a playlist" + ColorConsole.ANSI_RESET
                + " | " + ColorConsole.ANSI_CYAN + "2) To select a playlist" + ColorConsole.ANSI_RESET
                + " | " + ColorConsole.ANSI_RED + "3) To delete a playlist" + ColorConsole.ANSI_RESET
                + " | " + ColorConsole.ANSI_PURPLE + "4) To rename a playlist" + ColorConsole.ANSI_RESET
                + " | \n---------------");


        if(sc.hasNextInt()){
            int userChoice = sc.nextInt();
            if(userChoice < 5){
                playlistMenu(userChoice,listOfPLaylist,sc);
            }
            System.out.println("Please enter a valid number : ");
            commandPlaylist(sc,listOfPLaylist);
        }else {
            System.out.println("Please enter a valid number : ");
            sc.nextLine();
            commandPlaylist(sc, listOfPLaylist);
        }
    }


    public static void playlistMenu(int userChoice, PlaylistsList listOfPlaylist, Scanner sc) throws IOException, AlreadyExistingElementException {

        switch (userChoice) {
            case 0:/** Go back to menu */
                Menu();
                break;
            case 1: /** Add a new playlist */
                System.out.println("Name of the playlist : ");
                sc.nextLine();
                String playlistName = sc.nextLine();
                try{
                    listOfPlaylist.append(playlistName);
                    System.out.println("You added : " +  playlistName + " in your playlist.");

                }catch (AlreadyExistingElementException ignored){



                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                displayPlaylists(sc);



                break;
            case 2: /** Select a playlist */
                System.out.println(ColorConsole.ANSI_CYAN
                        + "Choose a number to select a playlist"
                        + "\n Enter '0' to go back"
                        + ColorConsole.ANSI_RESET);

                if(sc.hasNextInt()){
                    int userChoiceSelectMode = sc.nextInt();
                    if(userChoiceSelectMode == 0){
                        displayPlaylists(sc);
                    }
                    if(userChoiceSelectMode <= listOfPlaylist.get().size() ){
                        int element= userChoiceSelectMode - 1;
                        displaySinglePlaylists(sc,listOfPlaylist,element);
                    }else {
                        playlistMenu(2,listOfPlaylist,sc);
                    }

                }else {
                    sc.next();
                    playlistMenu(2,listOfPlaylist,sc);
                }
                break;
            case 3: /** Delete a playlist*/
                System.out.println(
                        ColorConsole.ANSI_RED
                                + "Choose a number to delete a playlist"
                                + "Enter '0' to go back"
                                + ColorConsole.ANSI_RESET);

                if(sc.hasNextInt()){
                    int userChoiceToDelete = sc.nextInt();
                    if(userChoiceToDelete == 0){
                        displayPlaylists(sc);
                    }
                    if(userChoiceToDelete <= listOfPlaylist.get().size()){
                        listOfPlaylist.remove(userChoiceToDelete - 1);
                        System.out.println("Playlist deleted successfully");
                        displayPlaylists(sc);
                    }
                }else {
                    System.out.println("Please enter a valid number :");
                    sc.next();
                    playlistMenu(userChoice,listOfPlaylist,sc);
                }
                break;

            case 4: /** Rename a playlist*/
                System.out.println(
                        ColorConsole.ANSI_PURPLE
                                + "Choose a number to rename a playlist"
                                + "Enter '0' to go back"
                                + ColorConsole.ANSI_RESET);
                if(sc.hasNextInt()){
                    int userChoiceToRename = sc.nextInt();
                    if(userChoiceToRename == 0){
                        displayPlaylists(sc);
                    } else if (userChoiceToRename <= listOfPlaylist.get().size()) {
                        sc.nextLine();
                        rename(listOfPlaylist,userChoiceToRename-1,sc);
                        listOfPlaylist.writeIntoFile("serializationFiles/Play");
                        System.out.println("Playlist renamed successfully");
                        displayPlaylists(sc);
                    }else {
                        System.out.println("Please enter a valid number :");
                        sc.next();
                        playlistMenu(userChoice,listOfPlaylist,sc);
                    }
                }else {
                    System.out.println("Please enter a valid number :");
                    sc.next();
                    playlistMenu(userChoice,listOfPlaylist,sc);
                }
                break;

            default:
                System.out.println("Please enter a valid number : ");
                sc.nextLine();
                playlistMenu(userChoice,listOfPlaylist,sc);
                break;
        }
    }

    public static void displaySinglePlaylists(Scanner sc, PlaylistsList listOfPlaylist, int index) throws IOException, AlreadyExistingElementException {

        PlayList playList = listOfPlaylist.get().get(index);
        int i = 1;
        if(playList.getSongs().isEmpty()){
            System.out.println("You don't have any song in : " + playList.getName());
            displayPlaylists(sc);
        }else {
            for(Song element : playList.getSongs()) {
                System.out.println("\n" + i + ") "+ element.toString());
                i++;
            }
            System.out.println("---------------\n" + "Select a number : " + " | " + ColorConsole.ANSI_BLUE + "0)" + " for Menu " + ColorConsole.ANSI_RESET + " | " +
                    ColorConsole.ANSI_CYAN + "1) to Select" + ColorConsole.ANSI_RESET +
                    " | " + ColorConsole.ANSI_RED + "2) to " +
                    "Delete" + ColorConsole.ANSI_RESET + " | \n---------------");
            playlistCommand(sc, listOfPlaylist, index);
        }
    }

    public static void playlistCommand(Scanner sc,PlaylistsList playlistsList, int index) throws IOException, AlreadyExistingElementException {
        if (sc.hasNextInt()){
            PlayList playList = playlistsList.get().get(index);
            switch (sc.nextInt()){
                case 0 :
                    Menu();
                    break;
                case 1:
                    System.out.println("Choose your song : ");
                    DisplaySongLyrics(sc,playList.getSongs());
                    break;
                case 2:
                    System.out.println("Song to delete : ");
                    DeleteMode(sc, playlistsList, index);
                    displaySinglePlaylists(sc, playlistsList, index);
                    break;
                default:
                    System.out.println("Retry a valid number : ");
                    playlistCommand(sc,playlistsList,index);
                    break;
            }
        }else {
            System.out.println("Retry a valid number : ");
            sc.nextLine();
            playlistCommand(sc,playlistsList,index);
        }

    }

    public static void DeleteMode(Scanner sc,PlaylistsList playlistsList, int index) throws IOException, AlreadyExistingElementException {
        PlayList playList = playlistsList.get().get(index);
        if (sc.hasNextInt() ){
            int userChoice = sc.nextInt()-1;
            if (userChoice >= 0 && userChoice < playList.getSongs().size()){
                System.out.println(playList.getSongs().get(userChoice).getTitle() + " has been delete from : " + playList.getName());
                playList.remove(userChoice);
                playlistsList.writeIntoFile("serializationFiles/Play");
                displaySinglePlaylists(sc, playlistsList, index);
            }else {
                System.out.println("Retry a valid number");
                sc.nextLine();
                DeleteMode(sc, playlistsList, index);
            }
        }else {
            System.out.println("Retry a valid number");
            sc.nextLine();
            DeleteMode(sc, playlistsList, index);
        }
    }

    public static void rename(PlaylistsList playlists, int index, Scanner sc) {
        PlayList playList = playlists.get().get(index);
        System.out.println("New name :");
        String name;
        do {
            name = sc.nextLine();
            if((playlists.isAlreadyInPlaylistsList(new PlayList(name))))
                System.out.println("already existant, retry");

        } while((playlists.isAlreadyInPlaylistsList(new PlayList(name))));


        playList.setName(name);
    }
}
