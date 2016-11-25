package getpubcommandline.ui.commands.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import getpubcommandline.ui.StringChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.ColorTerminal;
import getpublication.json.publication.JsonPublication;

public class RemoveProjectCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        JsonPublication jsonPublication = context.getJsonPublication();
        jsonPublication.load();

        StringChooser stringChooser = new StringChooser();
        stringChooser.setTitle("type a number of one project:");
        List<String> projects = new ArrayList<>(jsonPublication.getProjects());
        Collections.sort(projects);
        
        for (String projectName : projects) {
            stringChooser.addString(projectName);
        }

        String selectedProject = stringChooser.run();
        if (selectedProject != null && !selectedProject.equals("")) {
            List<String> favProjects = jsonPublication.getFavProjects();
            if (favProjects.contains(selectedProject)) {
                jsonPublication.removeFavProject(selectedProject);
            }
            
            jsonPublication.removeProject(selectedProject);
            
            jsonPublication.save();
            if (context.getProject() != null
                    && context.getProject().getName().equals(selectedProject)) {
                context.setProject(null);
            }
            System.out.println("project " + selectedProject + " removed");
        } else {
            System.out.println("remove canceled");
        }
    }

    @Override
    public String getCommandName() {
        ColorTerminal c = new ColorTerminal();
        return c.getRed()+"remove project"+c.getReset();
    }

}
