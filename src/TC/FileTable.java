package TC;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.Date;
import java.util.List;

public class FileTable extends JTable {
    DND passalert;
    Listener alert;
    FileTable(FileTableModel model) {
        super(model);
        setShowVerticalLines(false);
        setLayout(new BorderLayout());
        setSizeOfColumns();
        setRowHeight(30);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setAutoCreateRowSorter(true);
        getTableHeader().setReorderingAllowed(false);
        setDragEnabled(true);
        setFillsViewportHeight(true);

    }

    public void setSizeOfColumns() {
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMaxWidth(5);
            }
        }
    }

    void setData(String path, FileTableModel model) {
        model.setFiles(new File(path).listFiles());
        setTransferHandler(passalert = new DND(this, model, path));
        passalert.setAlertListener(alert);
    }

    public void removeSelectedRows(FileTable table, FileTableModel model, DirectPath path) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                File deletefile = model.getFile(table.convertRowIndexToModel(selectedRows[i]));
                if (deletefile.isDirectory()) {
                    deleteDirectory(deletefile);
                } else {
                    deletefile.delete();
                }
                setData(path.getText(), model);
            }
        }
    }
    boolean deleteDirectory(File directoryToDelete) {
        File[] allContents = directoryToDelete.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToDelete.delete();
    }
    void setAlert(Listener listener) {
        this.alert = listener;
    }
}






