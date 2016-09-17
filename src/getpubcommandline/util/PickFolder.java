package getpubcommandline.util;

import java.io.File;

import javax.swing.JFileChooser;

import getpublication.util.folder.chooser.DialogFolderChooser;

public class PickFolder implements DialogFolderChooser {

    @Override
    public String pickFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);

        File file = fileChooser.getSelectedFile();
        if (file == null) {
            return "";
        }

        return file.getPath();
    }

}
