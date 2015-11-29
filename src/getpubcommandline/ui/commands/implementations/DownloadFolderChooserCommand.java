package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.db.json.basicconfig.JsonConfig;
import getpublication.folders.DownloadFolder;
import getpublication.util.folder.chooser.DialogFolderChooser;

public class DownloadFolderChooserCommand implements Command {
    
    @Override
    public void action(ContextCommand context) {
        String path = DialogFolderChooser.pickFolder();
        if (path != null && !path.equals("")) {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.load();
            jsonConfig.setPathToDownloadFolder(path);
            jsonConfig.save();
            DownloadFolder downloadFolder = context.getDownloadFolder();
            downloadFolder.setPath(path);
            System.out.println("folder chooser successful operation!");
        } else {
            System.out.println("folder chooser operation canceled");
        }
    }

    @Override
    public String getCommandName() {
        return "set download folder";
    }
}
