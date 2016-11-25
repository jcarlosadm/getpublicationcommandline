package getpubcommandline.ui.commands.implementations;

import java.util.Collections;
import java.util.List;

import getpubcommandline.ui.StringChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;

public class AddFavoriteProjectCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        
        context.getJsonPublication().load();
        List<String> projects = context.getJsonPublication().getProjects();
        if (projects.isEmpty()) {
            System.out.println("add at least one project first!");
            return;
        }
        
        Collections.sort(projects);
        
        StringChooser stringChooser = new StringChooser();
        List<String> tempFavProjects = context.getJsonPublication().getFavProjects();
        for (String project : projects) {
            if (!tempFavProjects.contains(project)) {
                stringChooser.addString(project);
            }
        }
        
        stringChooser.setTitle("select one project:");
        String selectedProject = stringChooser.run();
        if (selectedProject == null || selectedProject.isEmpty()) {
            System.out.println("operation canceled.");
            return;
        }
        
        context.getJsonPublication().addFavProject(selectedProject);
        context.getJsonPublication().save();
        
        (new UpdateFavProjects()).action(context);
        
        System.out.println("done!");
    }

    @Override
    public String getCommandName() {
        return "add favorite project";
    }

}
