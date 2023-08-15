package app.lyricsapp.model;
// -*- coding: utf-8 -*-
public enum LangagesText {
    FRENCH(
            "Titre",
            "Artiste",
            "Paroles",
            "Recherche par titre et artiste",
            "Recherche par paroles",
            "Favoris",
            "Favoris",
            "Vos favoris",
            "Chansons populaire uniquement   ",
            "Rechercher",
            "Veuillez remplir les deux champs.",
            "Le champ ne peut pas être vide.",
            "Les paroles entrées contiennent des mots interdits.",
            "L'artiste entré contient des mots interdits.",
            "Le titre entré contient des mots interdits.",
            "Veuillez entrer au moins 2 caractères.",
            " Chansons",
            "Mes playlists",
            "Artiste & Titre",
            "Mode de recherche",
            "Choisissez un mode de recherche...",
            "Nouvelle Playlist",
            "Entrez le nom de la Playlist...",
            "Nom de la Playlist :",
            " a bien été ajoutée!",
            "Playlist",
            "Choisissez une playlist :",
            " est déjà dans",
            "Nouvelle chanson",
            "Acceder aux playlist",
            "Confirmer la suppression",
            "Êtes vous sur de vouloir supprimer?",
            "Annuler",
            "Renommer",
            "La playlist a bien été renommée"

    ),
    ENGLISH(
            "Title",
            "Artist",
            "Lyrics",
            "Search by title and artist",
            "Search by lyrics",
            "Favorites",
            "Favorites",
            "Your favorites",
            "Display popular songs only  ",
            "Search",
            "Please fill both fields.",
            "This field cannot be empty.",
            "The entered text contains forbidden words.",
            "The entered title contains forbidden words.",
            "Your input contains a banned word. Please try again.",
            "Please enter at least 2 characters.",
            " Songs",
            "My playlists",
            "Artist & Title",
            "Research Mode",
            "Choose a research mode :",
            "New Playlist",
            "Enter Playlist's name",
            "Playlist's name :",
            " has been added sucessfully!",
            "Playlists",
            "Choose a playlist :",
            " is already in ",
            "New Song",
            "Access the playlist",
            "Confirm deletion",
            "Are you sure you want to delete?",
            "Cancel",
            "Rename",
            "Playlist has been renamed succefully"

    ),
    SPANISH(
            "Título",
            "Artista",
            "Letra",
            "Búsqueda por título y artista",
            "Búsqueda por letra",
            "Títulos favoritos",
            "Títulos favoritos",
            "Mis título favoritos",
            "Mostrar solo los canciones populares  ",
            "Buscar",
            "Por favor complete ambos campos.",
            "Este campo no puede estar vacío.",
            "El texto introducido contiene palabras prohibidas.",
            "El título ingresado contiene palabras prohibidas.",
            "Su entrada contiene una palabra prohibida. Inténtalo de nuevo.",
            "Por favor ingrese al menos 2 caracteres.",
            " Canciones",
            "Listas de reproducción",
            "Artista & Título",
            "Modo de búsqueda",
            "Elija un modo de búsqueda",
            "Nueva lista de reproducción",
            "Introduce el nombre de la lista de reproducción",
            "Nombre de la lista de reproducción :",
            " se ha agregado con éxito!",
            "Lista de reproducción",
            "Elija una lista de reproducción",
            " ya esta en ",
            "Nueva Canción",
            "lista de reproducción",
            "Confirmar la eliminación",
            "¿Estas seguro que quieres borrarlo?",
            "Anular",
            "Renombrar",
            "La lista de reproducción ha sido renombrada"
    );

    //Attributes
    private String title;
    private String artist;
    private String lyrics;
    private String searchByTitleArtist;
    private String searchByLyrics;
    private String favorite;
    private String favoritePage;
    private String favoritePageTitle;
    private String restrictLabel;
    private String searchLabel;
    private String emptyFieldArtistTitle;
    private String emptyField;
    private String bannedWordLyrics;
    private String bannedWordArtist;
    private String bannedWordTitle;
    private String shortInput;
    private String songs;
    private String playlists;
    private String artistAndTitle;
    private String researchMode;
    private String pleaseChooseResearchMode;
    private String newPlaylist;
    private String enterPlaylistName;
    private String playlistName;
    private String hasBeenAddedSuccessfully;
    private String playlist;
    private String choosePlaylist;
    private String alreadyExists;
    private String newSong;
    private String goToPlaylist;

    private  String confirmDelete;

    private  String areYouSure;

    private  String cancelText;
    private String rename;
    private String hasBeenRenamed;

    //Constructor
    private LangagesText(
            String title,
            String artist,
            String lyrics,
            String searchByTitleArtist,
            String searchByLyrics,
            String favorite,
            String favoritePage,
            String favoritePageTitle,
            String restrictLabel,
            String searchLabel,
            String emptyFieldArtistTitle,
            String emptyField,
            String bannedWordLyrics,
            String bannedWordArtist,
            String bannedWordTitle,
            String shortInput,
            String songs,
            String playlists,
            String artistAndTitle,
            String researchMode,
            String pleaseChooseResearchMode,
            String newPlaylist,
            String enterPlaylistName,
            String playlistName,
            String hasBeenAddedSuccessfully,
            String playlist,
            String choosePlaylist,
            String alreadyExists,
            String newSong,
            String goToPlaylist,
            String confirmDelete,
            String areYouSure,
            String cancelText,
            String rename,
            String hasBeenRenamed

                                ) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        this.searchByTitleArtist = searchByTitleArtist;
        this.searchByLyrics = searchByLyrics;
        this.favorite = favorite;
        this.favoritePage = favoritePage;
        this.favoritePageTitle = favoritePageTitle;
        this.restrictLabel = restrictLabel;
        this.searchLabel = searchLabel;
        this.emptyFieldArtistTitle = emptyFieldArtistTitle;
        this.emptyField = emptyField;
        this.bannedWordLyrics = bannedWordLyrics;
        this.bannedWordArtist = bannedWordArtist;
        this.bannedWordTitle = bannedWordTitle;
        this.shortInput = shortInput;
        this.songs = songs;
        this.playlists = playlists;
        this.artistAndTitle = artistAndTitle;
        this.researchMode = researchMode;
        this.pleaseChooseResearchMode = pleaseChooseResearchMode;
        this.newPlaylist = newPlaylist;
        this.enterPlaylistName = enterPlaylistName;
        this.playlistName = playlistName;
        this.hasBeenAddedSuccessfully = hasBeenAddedSuccessfully;
        this.playlist = playlist;
        this.choosePlaylist = choosePlaylist;
        this.alreadyExists = alreadyExists;
        this.newSong = newSong;
        this.goToPlaylist = goToPlaylist;
        this.confirmDelete = confirmDelete;
        this.areYouSure = areYouSure;
        this.cancelText = cancelText;
        this.rename = rename;
        this.hasBeenRenamed = hasBeenRenamed;

    }

    public String getArtist() {
        return artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getBannedWordArtist() {
        return bannedWordArtist;
    }

    public String getBannedWordLyrics() {
        return bannedWordLyrics;
    }

    public String getBannedWordTitle() {
        return bannedWordTitle;
    }

    public String getSearchByTitleArtist() {
        return searchByTitleArtist;
    }

    public String getEmptyField() {
        return emptyField;
    }

    public String getEmptyFieldArtistTitle() {
        return emptyFieldArtistTitle;
    }

    public String getFavorite() {
        return favorite;
    }

    public String getFavoritePage() {
        return favoritePage;
    }

    public String getFavoritePageTitle() {
        return favoritePageTitle;
    }

    public String getRestrictLabel() {
        return restrictLabel;
    }

    public String getSearchByLyrics() {
        return searchByLyrics;
    }

    public String getTitle() {
        return title;
    }

    public String getSearchLabel() {
        return searchLabel;
    }

    public String getShortInput() {
        return shortInput;
    }
    public String getSongs() {
        return songs;
    }

    public String getPlaylists() {
        return playlists;
    }

    public String getArtistAndTitle() {
        return artistAndTitle;
    }
    public String getResearchMode() {
        return researchMode;
    }
    public String getPleaseChooseResearchMode() {
        return pleaseChooseResearchMode;
    }
    public String getNewPlaylist() {
        return newPlaylist;
    }
    public String getEnterPlaylistName() {
        return enterPlaylistName;
    }
    public String getPlaylistName() {
        return playlistName;
    }
    public String getHasBeenAddedSuccessfully() {
        return hasBeenAddedSuccessfully;
    }

    public String getPlaylist() {
        return playlist;
    }

    public String getChoosePlaylist() {
        return choosePlaylist;
    }

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public String getNewSong() {
        return newSong;
    }

    public String getGoToPlaylist() {return goToPlaylist;}

    public String getConfirmDelete() {
        return confirmDelete;
    }

    public String getAreYouSure() {
        return areYouSure;
    }

    public String getCancelText() {
        return cancelText;
    }

    public String getRename() {
        return rename;
    }

    public String getHasBeenRenamed() {
        return hasBeenRenamed;
    }
}


