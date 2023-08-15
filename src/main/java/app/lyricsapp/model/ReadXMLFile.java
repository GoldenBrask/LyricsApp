package app.lyricsapp.model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadXMLFile {

    public static   Song GetSong(InputStream input){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Song> songArrayList = new ArrayList<Song>();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document =  builder.parse(input);
            NodeList nodeList = document.getElementsByTagName("GetLyricResult");
            document.getDocumentElement().normalize();



            NodeList children =nodeList.item(0).getChildNodes();
            return CreateSong(children);


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Song CreateSong(NodeList children) {

        Song song = new Song();

        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE && node.hasChildNodes()){
                Element element = (Element) node;

                //song
                if("Song".equals(element.getTagName())){ song.setTitle(element.getTextContent()); }
                if("TrackId".equals(element.getTagName())){ song.setTrackID(Integer.parseInt(element.getTextContent())) ; }
                if("SongUrl".equals(element.getTagName())){ song.setURL(element.getTextContent());  }
                if("SongRank".equals(element.getTagName())){ song.setSongRank(Integer.parseInt(element.getTextContent()));  }
                if("Checksum".equals(element.getTagName())){ song.setChecksum(element.getTextContent()); }
                if("LyricCovertArtUrl".equals(element.getTagName())){ song.setCoverUrl(element.getTextContent()); }
                if("TrackCheckSum".equals(element.getTagName())){ song.setTrackchecksum(element.getTextContent()); }

                //Artist
                if("Artist".equals(element.getTagName())){ song.setArtist(element.getTextContent());  }
                if("ArtistUrl".equals(element.getTagName())){ song.setArtistURL(element.getTextContent());  }

                //lyrics
                if("LyricId".equals(element.getTagName())){song.setLyrickID(Integer.parseInt(element.getTextContent()));}
                if("Lyric".equals(element.getTagName())){ song.setLyric(element.getTextContent()); }
                if("LyricChecksum".equals(element.getTagName())){ song.setLyricSum(element.getTextContent()); }
                if("LyricSong".equals(element.getTagName())){ song.setTitle(element.getTextContent()); }
                if("LyricArtist".equals(element.getTagName())){ song.setLyricArtist(element.getTextContent()); }
                if("LyricUrl".equals(element.getTagName())){ song.setLyricUrl(element.getTextContent()); }
                if("LyricRank".equals(element.getTagName())){ song.setLyricRank(Integer.parseInt(element.getTextContent())); }
            }
        }
        return song;
    }

    public static   String GetCover(InputStream input){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Song> songArrayList = new ArrayList<>();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document =  builder.parse(input);
            NodeList nodeList = document.getElementsByTagName("LyricCovertArtUrl");
            document.getDocumentElement().normalize();



            String children =nodeList.item(0).getTextContent();
            return children;
            //return CreateSong(children);


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public  static List<Song> GetListSongs(InputStream input ){

        List<Song> songList = new ArrayList<Song>();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(input);
            NodeList nodeList = document.getElementsByTagName("SearchLyricResult");

            for (int i = 0; i < nodeList.getLength()-1; i++) {
                NodeList children =nodeList.item(i).getChildNodes();
                //Verifie si la musique contient des paroles
                if(CreateSong(children).getLyrickID()!=0){
                    songList.add(CreateSong(children));
                }
            }
            return songList;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return songList;

    }

















}
