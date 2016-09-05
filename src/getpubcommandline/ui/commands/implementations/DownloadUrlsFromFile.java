package getpubcommandline.ui.commands.implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.project.Project;
import getpublication.project.ProjectBuilder;

public class DownloadUrlsFromFile implements Command {

    @Override
    public void action(ContextCommand context) {
        List<String> urlStrings = new ArrayList<>();
        
        try {
            File file = this.fileChoose();
            if (file == null) {
                System.out.println("exiting...");
                return;
            }
            
            BufferedReader bReader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = bReader.readLine()) != null) {
                urlStrings.add(line);
            }
            
            bReader.close();
            
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
        } catch (Exception e) {
            System.out.println("error to read file");
            e.printStackTrace();
        }
    }
    
    private File fileChoose(){
        JFileChooser fileChooser = new JFileChooser();
        int val = fileChooser.showOpenDialog(null);
        
        if (val == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        
        return null;
    }

    @Override
    public String getCommandName() {
        return "download urls from file";
    }

}
