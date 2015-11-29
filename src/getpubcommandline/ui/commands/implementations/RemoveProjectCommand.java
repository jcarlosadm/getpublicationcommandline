package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.StringChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.db.json.JsonBasicOperations;
import getpublication.db.json.publication.JsonPublication;

public class RemoveProjectCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        JsonPublication jsonPublication = context.getJsonPublication();
        ((JsonBasicOperations) jsonPublication).load();

        StringChooser stringChooser = new StringChooser();
        stringChooser.setTitle("type a number of one project:");
        for (String projectName : jsonPublication.getProjects()) {
            stringChooser.addString(projectName);
        }

        String selectedProject = stringChooser.runWithIndex();
        if (selectedProject != null && !selectedProject.equals("")) {
            jsonPublication.removeProject(selectedProject);

            ((JsonBasicOperations) jsonPublication).save();
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
        return "remove project";
    }

}
