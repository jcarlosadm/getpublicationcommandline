package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.CommandChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.db.json.JsonBasicOperations;
import getpublication.db.json.publication.JsonPublication;
import getpublication.db.json.publication.mangahost.JsonMangahost;
import getpublication.project.SiteName;

public class MangahostChooserCommand implements Command{
    
    @Override
    public void action(ContextCommand context) {
        context.setSiteName(SiteName.MANGAHOST);
        JsonPublication jsonPublication = new JsonMangahost();
        ((JsonBasicOperations) jsonPublication).load();
        context.setJsonPublication(jsonPublication);
        
        Command addProject = new AddProjectCommand();
        Command removeProject = new RemoveProjectCommand();
        Command selectProject = new ProjectChooserCommand();
        Command showChapters = new ShowChaptersCommand();
        Command chapterChooser = new ChapterChooserCommand();
        Command chapterArrayChooser = new ChapterArrayChooserCommand();
        Command exitCommand = new ExitCommand();
        
        CommandChooser commandChooser = new CommandChooser();
        commandChooser.addContext(context);
        commandChooser.setTitle("select a command:");
        commandChooser.addCommand(addProject);
        commandChooser.addCommand(removeProject);
        commandChooser.addCommand(selectProject);
        
        boolean exit = false;
        while (!exit) {
            if (context.getProject() != null) {
                commandChooser.removeCommand(exitCommand);
                commandChooser.addCommand(showChapters);
                commandChooser.addCommand(chapterChooser);
                commandChooser.addCommand(chapterArrayChooser);
                commandChooser.addCommand(exitCommand);
            } else {
                commandChooser.removeCommand(exitCommand);
                commandChooser.removeCommand(chapterArrayChooser);
                commandChooser.removeCommand(chapterChooser);
                commandChooser.removeCommand(showChapters);
                commandChooser.addCommand(exitCommand);
            }
            
            Command selectedCommand = commandChooser.run();
            if (selectedCommand instanceof ExitCommand) {
                exit = true;
            }
        }
    }

    @Override
    public String getCommandName() {
        return "Mangahost";
    }

}
