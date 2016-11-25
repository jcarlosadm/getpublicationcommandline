package getpubcommandline.ui.commands.implementations;

import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.ColorTerminal;

public class ShowNewsFavProjectCommand implements Command {

    private static final int MAX_COUNT = 5;

    @Override
    public void action(ContextCommand context) {
        context.getJsonPublication().load();
        List<String> favProjects = context.getJsonPublication()
                .getFavProjects();
        if (favProjects == null || favProjects.isEmpty()) {
            System.out.println("you do not have any favorite project!");
            return;
        }

        ColorTerminal c = new ColorTerminal();

        for (String projectName : favProjects) {
            List<String> chaptersRecorded = context.getJsonPublication()
                    .getChapters(projectName);
            String lastChapter = context.getJsonPublication()
                    .getLastChapter(projectName);
            int indexLastChapter = chaptersRecorded.indexOf(lastChapter);

            if (indexLastChapter < (chaptersRecorded.size() - 1)) {
                System.out.println(
                        "Project: " + c.getRed() + projectName + c.getReset());
                
                int count = 0;
                for (int index = indexLastChapter + 1; index < chaptersRecorded
                        .size(); ++index) {
                    ++count;
                    if (count >= MAX_COUNT) {
                        System.out.print(c.getBlue()+"... "+c.getReset());
                        break;
                    }
                    System.out.print(c.getBlue() + chaptersRecorded.get(index)
                            + c.getReset() + " ");
                }
                System.out.println("\n");
            }
        }
    }

    @Override
    public String getCommandName() {
        return "show unread chapters from favorite projects";
    }

}
