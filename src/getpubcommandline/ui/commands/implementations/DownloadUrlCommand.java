package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.project.Project;
import getpublication.project.ProjectBuilder;
import getpublication.util.UserInput;

public class DownloadUrlCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        System.out.println("type an url:");
        String urlString = UserInput.getInput();
        
        ProjectBuilder builder = context.getProjectBuilder();
        builder.setName("");
        builder.setUrlPart(urlString);
        builder.setAnonymousMode(context.isAnonymousMode());
        
        Project project = builder.build();
        System.out.println("downloading...");
        project.downloadChapter("", context.getDownloadFolder());
    }

    @Override
    public String getCommandName() {
        return "download from url";
    }

}
