package getpubcommandline.ui.commands.implementations;

import java.util.ArrayList;
import java.util.List;

import getpubcommandline.ui.CommandChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.db.json.JsonBasicOperations;
import getpublication.db.json.publication.JsonPublication;
import getpublication.db.json.publication.mangahost.JsonMangahost;
import getpublication.project.SiteName;

public class MangahostChooserCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        context.setSiteName(SiteName.MANGAHOST);
        JsonPublication jsonPublication = new JsonMangahost();
        ((JsonBasicOperations) jsonPublication).load();
        context.setJsonPublication(jsonPublication);

        Command addProject = new AddProjectCommand();
        Command removeProject = new RemoveProjectCommand();
        Command selectProject = new ProjectChooserCommand();

        List<Command> tempCommands = new ArrayList<>();
        tempCommands.add(new ShowChaptersCommand());
        tempCommands.add(new ChapterChooserCommand());
        tempCommands.add(new ChapterArrayChooserCommand());
        tempCommands.add(new ChapterRangeChooserCommand());

        Command exitCommand = new ExitCommand();

        CommandChooser commandChooser = new CommandChooser();
        commandChooser.addContext(context);
        commandChooser.setTitle("select a command:");
        commandChooser.addCommand(addProject);
        commandChooser.addCommand(removeProject);
        commandChooser.addCommand(selectProject);

        boolean exit = false;
        while (!exit) {
            commandChooser.removeCommand(exitCommand);
            for (Command tempCommand : tempCommands) {
                if (context.getProject() != null) {
                    commandChooser.addCommand(tempCommand);
                } else {
                    commandChooser.removeCommand(tempCommand);
                }
            }
            commandChooser.addCommand(exitCommand);

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
