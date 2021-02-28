package TC;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Frame extends JFrame {
    SidePanel leftside;
    SidePanel rightside;
    Frame() {
           super("Total Commander");
           setSize(750,700);
           setLayout(new GridLayout());

           //dodaje lewy Panel
            leftside = new SidePanel();
            add(leftside, BorderLayout.WEST);

            //dodaje prawy Panel
            rightside = new SidePanel();
            add(rightside, BorderLayout.EAST);

            leftside.setListener( new Listener() {
                @Override
                public void alertTable() {
                    File check = new File(rightside.directPath.getText());
                    if(check.exists()) {
                        rightside.table.setData(rightside.directPath.getText(), rightside.model);
                        rightside.table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            });
            rightside.setListener(new Listener() {
                @Override
                public void alertTable() {
                    File check = new File(leftside.directPath.getText());
                    if(check.exists()) {
                        leftside.table.setData(leftside.directPath.getText(), leftside.model);
                        leftside.table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            });



           setLocationRelativeTo(null);
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setVisible(true);

    }


}
