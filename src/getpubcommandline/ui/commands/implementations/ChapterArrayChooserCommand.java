package getpubcommandline.ui.commands.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.util.UserInput;

public class ChapterArrayChooserCommand implements Command {

    private static final int DOWNLOAD_TENTATIVES = 50;

    @Override
    public void action(ContextCommand context) {
        if (context.getProject() == null) {
            return;
        }

        context.getJsonPublication().load();

        List<String> chapterNames = context.getJsonPublication()
                .getChapters(context.getProject().getName());
        if (chapterNames == null) {
            return;
        }

        System.out.println(
                "type several chapters separeted by comma (or _exit_ to exit):");
        String stringUser = UserInput.getInput();

        if (stringUser.equals("_exit_")) {
            System.out.println("canceled operation");
            return;
        }

        List<String> selectedChapters = new ArrayList<>(
                Arrays.asList(stringUser.split(",")));
        
        for (Iterator<String> iterator = selectedChapters.iterator(); iterator
                .hasNext();) {
            String selectedChapter = (String) iterator.next();
            if (!chapterNames.contains(selectedChapter)) {
                System.out.println("chapter "+selectedChapter+" not found in database.");
                iterator.remove();
            }
        }

        if (selectedChapters.isEmpty()) {
            System.out.println("no chapter selected to download");
            return;
        }

        for (String selectedChapter : selectedChapters) {
            boolean success = false;
            int count = 0;
            while (!success && count <= DOWNLOAD_TENTATIVES) {
                success = context.getProject().downloadChapter(
                        selectedChapter.trim(), context.getDownloadFolder(),
                        context.getJsonPublication()
                                .getConvertImagesProperty());
                count++;
            }
        }
    }

    @Override
    public String getCommandName() {
        return "select several chapters (separated by comma)";
    }

}
