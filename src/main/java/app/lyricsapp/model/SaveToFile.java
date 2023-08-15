package app.lyricsapp.model;

import java.io.*;

public interface SaveToFile<T> extends Serializable {
    default void writeIntoFile(String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
    }

    default T getFromFile(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream stream = new FileInputStream(filePath);
        ObjectInputStream objStream = new ObjectInputStream(stream);
        return  (T) objStream.readObject();

    }
}
