package TC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteConfirmFrame extends JFrame {
    JPanel top;
    JPanel bottom;
    JButton yes;
    JButton no;
    JLabel confirm;

    DeleteConfirmFrame(FileTableModel model, FileTable table, DirectPath path, Listener listener) {
        super("Potwierdz usunięcie");
        setSize(400,100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);
        top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        add(top, BorderLayout.CENTER);
        confirm = new JLabel();
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirm.setText("Czy na pewno chcesz usunąć pliki(i)/folder(y)");
        top.add(confirm);
        bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        add(bottom, BorderLayout.SOUTH);
        yes = new JButton("Tak");
        bottom.add(yes);
        no = new JButton("Nie");
        bottom.add(no);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeSelectedRows(table, model, path);
                listener.alertTable();
                dispose();
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
