package TC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewFolderConfirmFrame extends JFrame {
    JButton potwierdz;
    JTextField nazwa;
    JLabel blad;
    NewFolderConfirmFrame(FileTableModel model, FileTable table, DirectPath path, Listener listener) {
        super("Nowy Folder");
        setSize(400,100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);
        add(blad = new JLabel(), BorderLayout.SOUTH);
        add( potwierdz = new JButton("utwórz"), BorderLayout.EAST);
        add( nazwa = new JTextField(), BorderLayout.CENTER);
        potwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File folder = new File(path.getText() + "\\" + nazwa.getText());
                if(folder.exists()){
                    blad.setText("Taki folder już istnieje");
                }
                else {
                    folder.mkdir();
                    dispose();
                    table.setData(path.getText(), model);
                    listener.alertTable();
                }
            }
        });
    }

}
