package TC;

import javax.swing.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class DirectPath extends JLabel {

    DirectPath() {
        getPath();
    }
    void getPath() {
        setText("C:\\");
    }

    void setPath(String path) {
        System.getProperty("dir");
        System.setProperty("dir", path);
        setText(System.getProperty("dir"));
    }
}
