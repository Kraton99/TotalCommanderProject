package TC;

import org.w3c.dom.UserDataHandler;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class DND extends TransferHandler {

    FileTable table;
    FileTableModel model;
    String path;
    String files = "";
    Listener alertListener;
    DND(FileTable table, FileTableModel model, String path)  {
        this.table = table;
        this.model = model;
        this.path = path;
    }
   public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
        }

   protected Transferable createTransferable(JComponent c) {
       ArrayList<String> stringFiles = new ArrayList<>();
        int[] rows = table.getSelectedRows();
        for(int row : rows){
            stringFiles.add(model.getFile(table.convertRowIndexToModel(row)).toString());
        }
        for(String file : stringFiles){
            files = files + file + "#!";
        }

       return new StringSelection(files);
   }

    public boolean canImport(TransferHandler.TransferSupport support) {
        boolean b = support.getComponent() == table && support.isDrop();
        support.setShowDropLocation(b);
        table.setCursor(b ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
        return b;
    }
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        FileTable.DropLocation dl = (FileTable.DropLocation)support.getDropLocation();
        Transferable t = support.getTransferable();
        String data;
        try {
            data = (String) t.getTransferData(DataFlavor.stringFlavor);
        }
        catch (Exception e) { return false; }

        String[] filesplit = data.split("#!");
        for(String files : filesplit) {
            String[] file = files.split("\\\\");
            if (dl.isInsertRow()) {
                File source = new File(files);
                if (source.isDirectory()) {
                    File dest = new File(path + "\\" + file[file.length - 1]);
                    try {
                        copyDirectoryR(source, dest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    File dest = new File(path + "\\" + file[file.length - 1]);
                    try {
                        FileInputStream fis = new FileInputStream(source);
                        FileOutputStream fos = new FileOutputStream(dest);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {

                            fos.write(buffer, 0, length);
                        }
                        fis.close();
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                File source = new File(files);
                String path1 = model.getFile(table.convertRowIndexToModel(dl.getRow())).toString();
                File dest = new File(path1 + "\\" + file[file.length - 1]);
                if (model.getFile(table.convertRowIndexToModel(dl.getRow())).isDirectory()) {
                    if (source.isDirectory()) {
                        try {
                            copyDirectoryR(source, dest);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            FileInputStream fis = new FileInputStream(source);
                            FileOutputStream fos = new FileOutputStream(dest);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) > 0) {

                                fos.write(buffer, 0, length);
                            }
                            fis.close();
                            fos.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return true;
    }

   protected void exportDone(JComponent c, Transferable t, int action) {
        if (action == MOVE) {
            table.setData(path, model);
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            alertListener.alertTable();
        }
    }
    void  setAlertListener(Listener listener){
        this.alertListener = listener;
    }

    public static void copyDirectory(File sourceDir, File targetDir)
            throws IOException {
        if (sourceDir.isDirectory()) {
            copyDirectoryR(sourceDir, targetDir);
        } else {
            Files.copy(sourceDir.toPath(), targetDir.toPath());
        }
    }
    private static void copyDirectoryR(File source, File target)
            throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }
        for (String child : source.list()) {
            copyDirectory(new File(source, child), new File(target, child));
        }
    }
}

