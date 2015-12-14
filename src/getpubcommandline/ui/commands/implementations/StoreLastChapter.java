package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.json.publication.JsonPublication;
import getpublication.project.Project;
import getpublication.util.UserInput;

public class StoreLastChapter implements Command {

    @Override
    public void action(ContextCommand context) {
        JsonPublication jsonPublication = context.getJsonPublication();
        Project project = context.getProject();
        if (jsonPublication == null || project == null) {
            return;
        }
        
        jsonPublication.load();

        String lastChapter = jsonPublication.getLastChapter(project.getName());
        if (lastChapter == null || lastChapter.equals("")) {
            System.out.println("last chapter not stored for the project "
                    + project.getName());
        } else {
            System.out.println("last chapter: " + lastChapter);
        }
        
        System.out.println("Change last Chapter? (type \"yes\" or \"no\")");
        if (!UserInput.getInput().toLowerCase().equals("yes")) {
            return;
        }
        
        System.out.print("type the last chapter: ");
        lastChapter = UserInput.getInput();
        
        jsonPublication.setLastChapter(project.getName(), lastChapter);
        jsonPublication.save();
    }

    @Override
    public String getCommandName() {
        return "Store Last Chapter";
    }

}
