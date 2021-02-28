package TC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class DiskComboBox extends JComboBox {
    DiskComboBox() {
        setComboBoxWithDisks();

    }

    void setComboBoxWithDisks() {
        File[] paths;
        paths = File.listRoots();
        setSize(200, 100);
        for (File path : paths) {
            addItem(path);
        }
        addItem("Desktop");
    }


}

