package getpubcommandline.ui.commands.implementations;

import java.util.ArrayList;
import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.project.Project;
import getpublication.project.ProjectBuilder;
import getpublication.util.UserInput;

public class DownloadUrlListCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        // TODO Auto-generated method stub
        boolean exit = false;
        List<String> urlStrings = new ArrayList<>();
        String urlString = "";
        
        while (!exit) {
            System.out.println("type an url or 0 to run url list or -1 to cancel:");
            
            urlString = UserInput.getInput();
            
            int decodedEntry = this.decodeEntry(urlString);
            if (decodedEntry < 0) {
                System.out.println("exiting...");
                return;
            } else if (decodedEntry == 0) {
                exit = true;
            } else {
                urlStrings.add(urlString);
            }
        }
        
        for (String url : urlStrings) {
            ProjectBuilder builder = context.getProjectBuilder();
            builder.setName("");
            builder.setUrlPart(url);
            builder.setAnonymousMode(context.isAnonymousMode());

            Project project = builder.build();
            System.out.println("downloading...");
            project.downloadChapter("", context.getDownloadFolder(),
                    context.getJsonPublication().getConvertImagesProperty());
        }
        
        System.out.println("all urls downloaded");
    }
    
    private int decodeEntry (String string) {
        
        int value = 0;
        try {
            value = Integer.parseInt(string);
        } catch (Exception e) {
            return 1;
        }
        
        if (value < 0) {
            return -1;
        }
        
        return 0;
    }

    @Override
    public String getCommandName() {
        // TODO Auto-generated method stub
        return "download from url list";
    }

}
