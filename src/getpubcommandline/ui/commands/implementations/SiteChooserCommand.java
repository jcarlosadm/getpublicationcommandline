package getpubcommandline.ui.commands.implementations;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import getpubcommandline.ui.CommandChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.folders.UserFolder;
import getpublication.json.basicconfig.JsonConfig;
import getpublication.util.logs.LogFileWriter;

public class SiteChooserCommand implements Command {

    private List<Command> siteCommands = new ArrayList<>();
    
    public void addSiteCommand(Command command){
        this.siteCommands.add(command);
    }
    
    @Override
    public void action(ContextCommand context) {
        String timeMark = "";
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        timeMark += sFormat.format(Calendar.getInstance().getTime());
        
        File logFile = new File(UserFolder.getPathToLogFolder() + File.separator + timeMark);
        LogFileWriter logFileWriter = LogFileWriter.getNewLogFile(logFile);
        
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
        
        try {
            logFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommandName() {
        return "select a site";
    }

}
