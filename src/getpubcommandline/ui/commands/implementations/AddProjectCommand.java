package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.db.json.publication.JsonPublication;
import getpublication.db.json.publication.PropertiesName;
import getpublication.util.UserInput;

public class AddProjectCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        System.out.println("type a project name:");
        String projectName = UserInput.getInput();

        System.out.println("type the url part of the project:");
        String urlPart = UserInput.getInput();

        JsonPublication jsonPublication = context.getJsonPublication();

        jsonPublication.load();

        jsonPublication.addProject(projectName);
        jsonPublication.addProjectProperty(projectName,
                PropertiesName.NAME_IN_URL, urlPart);

        jsonPublication.save();
        System.out.println("project "+projectName+" added");
    }

    @Override
    public String getCommandName() {
        return "add project";
    }
}
