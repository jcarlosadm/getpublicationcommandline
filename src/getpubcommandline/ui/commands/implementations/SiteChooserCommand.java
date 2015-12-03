package getpubcommandline.ui.commands.implementations;

import java.util.ArrayList;
import java.util.List;

import getpubcommandline.ui.CommandChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.json.basicconfig.JsonConfig;

public class SiteChooserCommand implements Command {

    private List<Command> siteCommands = new ArrayList<>();
    
    public void addSiteCommand(Command command){
        this.siteCommands.add(command);
    }
    
    @Override
    public void action(ContextCommand context) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.load();
        context.getDownloadFolder().setPath(jsonConfig.getPathToDownloadFolder());
        
        Command exitCommand = new ExitCommand();
        Command downloadFolderChooserCommand = new DownloadFolderChooserCommand();
        Command anonymousCommand = new SetAnonymousCommand();
        
        CommandChooser commandChooser = new CommandChooser();
        commandChooser.setTitle("select a command:");
        commandChooser.addContext(context);
        commandChooser.addCommand(downloadFolderChooserCommand);
        commandChooser.addCommand(anonymousCommand);
        
        for (Command siteCommand : this.siteCommands) {
            commandChooser.addCommand(siteCommand);
        }
        
        commandChooser.addCommand(exitCommand);
        
        boolean exit = false;
        while (!exit) {
            Command selectedCommand = commandChooser.run();
            if (selectedCommand instanceof ExitCommand) {
                exit = true;
            }
        }
    }

    @Override
    public String getCommandName() {
        return "select a site";
    }

}
