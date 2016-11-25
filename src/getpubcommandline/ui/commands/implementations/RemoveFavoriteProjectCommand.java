package getpubcommandline.ui.commands.implementations;

import java.util.Collections;
import java.util.List;

import getpubcommandline.ui.StringChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;

public class RemoveFavoriteProjectCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        context.getJsonPublication().load();
        List<String> favProjects = context.getJsonPublication().getFavProjects();
        if (favProjects.isEmpty()) {
            System.out.println("you don't have any favorite project.");
            return;
        }
        
        Collections.sort(favProjects);
        
        StringChooser stringChooser = new StringChooser();
        stringChooser.setStringList(favProjects);
        stringChooser.setTitle("select one favorite project to remove:");
        
        String projectSelected = stringChooser.run();
        if (projectSelected == null || projectSelected.isEmpty()) {
            System.out.println("operation canceled");
            return;
        }
        
        context.getJsonPublication().removeFavProject(projectSelected);
        context.getJsonPublication().save();
        
        System.out.println("done!");
    }

    @Override
    public String getCommandName() {
        return "remove favorite project";
    }

}
