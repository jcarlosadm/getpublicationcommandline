package getpubcommandline.ui.commands.implementations;

import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.UpdateLastChapter;
import getpublication.project.Project;
import getpublication.util.UserInput;

public class ChapterChooserCommand implements Command {

    private static final int DOWNLOAD_TENTATIVES = 50;

    @Override
    public void action(ContextCommand context) {
        Project project = context.getProject();
        if (project == null) {
            System.out.println("project not selected");
            return;
        }

        String selectedChapter = "";
        boolean exit = false;
        while (!exit) {
            System.out.print(
                    "type a chapter name or number (or _exit_ to exit): ");
            selectedChapter = UserInput.getInput();
            context.getJsonPublication().load();
            List<String> chapterNames = context.getJsonPublication().getChapters(project.getName());

            if (selectedChapter.equals("_exit_")) {
                System.out.println("operation canceled");
                selectedChapter = "";
                exit = true;
            } else if (chapterNames != null && !chapterNames
                    .contains(selectedChapter)) {
                System.out.println(
                        "chapter not found in database");
            } else {
                System.out.println("chapter " + selectedChapter + " selected");
                exit = true;
            }
        }

        if (selectedChapter == null || selectedChapter.equals("")) {
            System.out.println("chapter not selected");
            return;
        }

        boolean success = false;
        int count = 0;
        while (!success && count <= DOWNLOAD_TENTATIVES) {
            success = project.downloadChapter(selectedChapter, context.getDownloadFolder(),
                    context.getJsonPublication().getConvertImagesProperty());
            count++;
            if (!success)
                System.out.println("trying again ("+count+"/"+DOWNLOAD_TENTATIVES+")");
        }
        
        if (success) {
            UpdateLastChapter.update(context, selectedChapter);
        }
    }

    @Override
    public String getCommandName() {
        return "select a chapter for download";
    }

}
