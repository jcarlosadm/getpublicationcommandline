package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.project.Project;
import getpublication.util.UserInput;

public class ChapterChooserCommand implements Command {

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

            if (selectedChapter.equals("_exit_")) {
                System.out.println("operation canceled");
                selectedChapter = "";
                exit = true;
            } else if (!project.getAllChapterNames()
                    .contains(selectedChapter)) {
                System.out.println(
                        "chapter not found in database. continue? (type yes or no)");
                String confirm = UserInput.getInput();
                if (confirm.toLowerCase().equals("yes")) {
                    System.out.println("you chose try to download chapter "
                            + selectedChapter);
                    exit = true;
                }
            } else {
                System.out.println("chapter " + selectedChapter + " selected");
                exit = true;
            }
        }

        if (selectedChapter == null || selectedChapter.equals("")) {
            System.out.println("chapter not selected");
            return;
        }

        project.downloadChapter(selectedChapter, context.getDownloadFolder());
    }

    @Override
    public String getCommandName() {
        return "select a chapter for download";
    }

}
