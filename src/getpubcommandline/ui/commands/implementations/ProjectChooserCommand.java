package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.StringChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.db.json.publication.JsonPublication;
import getpublication.db.json.publication.PropertiesName;
import getpublication.project.Project;
import getpublication.project.ProjectBuilder;

public class ProjectChooserCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        JsonPublication jsonPublication = context.getJsonPublication();
        if (jsonPublication == null) {
            System.out.println("error to get projects");
            return;
        }
        
        StringChooser stringChooser = new StringChooser();
        stringChooser.setTitle("select a project:");
        for (String projectName : jsonPublication.getProjects()) {
            stringChooser.addString(projectName);
        }
        String selectedProject = stringChooser.run();
        
        if (selectedProject == null || selectedProject.equals("")) {
            System.out.println("project not selected");
            return;
        }
        
        ProjectBuilder projectBuilder = context.getProjectBuilder();
        projectBuilder.setName(selectedProject);
        projectBuilder.setUrlPart(jsonPublication.getProjectProperty(selectedProject, 
                PropertiesName.NAME_IN_URL));
        projectBuilder.setAnonymousMode(context.isAnonymousMode());
        Project project = projectBuilder.build();
        
        context.setProject(project);
        System.out.println("project "+project.getName()+" selected");
    }

    @Override
    public String getCommandName() {
        return "select project";
    }
}
