package TC;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class SidePanel extends JPanel implements KeyListener{
    DiskComboBox topBox = new DiskComboBox();
    JPanel topPanel;
    DirectPath directPath = new DirectPath();
    FileTableModel model = new FileTableModel(new File("C:\\").listFiles());
    FileTable table = new FileTable(model);
    ReturnButton button = new ReturnButton();
    Listener alertListener;
    DND passalert;
    SidePanel() {
        setLayout(new BorderLayout());

        //dodaje gorny Panel
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        //dodaje box z dyskami
        topPanel.add(topBox, BorderLayout.NORTH);

        //dodaje tabele
        add(new JScrollPane(table), BorderLayout.CENTER);
        table.setDropMode(DropMode.ON_OR_INSERT);
        table.setTransferHandler(passalert = new DND(table, model, directPath.getText()));


        // dodaje label ze ścieżką
        topPanel.add(directPath, BorderLayout.WEST);
        directPath.setPath("C:\\");



        topPanel.add(button, BorderLayout.AFTER_LAST_LINE);


        topBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String path = cb.getSelectedItem().toString();
                directPath.setPath(path);
                if (path == "Desktop") {
                    table.setData(System.getProperty("user.home") + "\\Desktop", model);
                    directPath.setPath(System.getProperty("user.home") + "\\Desktop");
                } else {
                    directPath.setPath(path);
                    table.setData(path, model);
                }
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nextPath = "";
                if(!(directPath.getText().matches(".:\\\\") || directPath.getText().matches(".:"))) {
                    table.setData(directPath.getText() + "\\..", model);
                   directPath.setPath(directPath.getText());
                    String[] str = directPath.getText().split("\\\\");
                    for(int i = 0; i < str.length - 2; i++){
                        nextPath = nextPath + str[i] + "\\";
                    }
                    nextPath = nextPath + str[str.length - 2];
                    if(nextPath.matches(".:")){
                        nextPath = nextPath + "\\";
                    }
                   directPath.setPath(nextPath);

                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                String dirName = "";
                mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    dirName = model.getValueAt(table.convertRowIndexToModel(row),1).toString();
                    if(model.getFile(table.convertRowIndexToModel(row)).isDirectory()){
                        table.setData(model.getFile(table.convertRowIndexToModel(row)).toString(), model);
                        if(directPath.getText().matches(".:\\\\")){
                            directPath.setPath(directPath.getText()  + dirName);
                        }
                        else {
                           directPath.setPath(directPath.getText() + "\\" + dirName);
                        }
                    }
            }
            }
        });

        table.addKeyListener(this);

        setVisible(true);
    }
    void setListener(Listener listener) {
        this.alertListener = listener;
        passalert.setAlertListener(alertListener);
        table.setAlert(listener);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == 119){
            new DeleteConfirmFrame(model, table, directPath, alertListener);

    }
        else if(e.getKeyCode() == 27){
            table.clearSelection();
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            alertListener.alertTable();
        }
        else if(e.getKeyCode() == 118){
            new NewFolderConfirmFrame(model, table, directPath, alertListener);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}


