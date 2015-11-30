package getpubcommandline.ui.commands.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.util.UserInput;

public class ChapterArrayChooserCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        if (context.getProject() == null) {
            return;
        }

        List<String> chapterNames = context.getProject().getAllChapterNames();

        System.out.println(
                "type several chapters separeted by comma (or _exit_ to exit):");
        String stringUser = UserInput.getInput();

        if (stringUser.equals("_exit_")) {
            System.out.println("canceled operation");
            return;
        }

        List<String> selectedChapters = new ArrayList<>(
                Arrays.asList(stringUser.split(",")));

        for (Iterator<String> selectedChapterIterator = selectedChapters
                .iterator(); selectedChapterIterator.hasNext();) {
            String selectedChapter = ((String) selectedChapterIterator.next())
                    .trim();

            if (!chapterNames.contains(selectedChapter)) {
                System.out.print("chapter " + selectedChapter
                        + " not found in database. try download?"
                        + " (type yes or no) ");
                String userConfirm = UserInput.getInput();
                if (!userConfirm.toLowerCase().equals("yes")) {
                    selectedChapterIterator.remove();
                    System.out
                            .println("chapter " + selectedChapter + " removed");
                }
            }
        }

        if (selectedChapters.isEmpty()) {
            System.out.println("no chapter selected to download");
            return;
        }

        for (String selectedChapter : selectedChapters) {
            context.getProject().downloadChapter(selectedChapter.trim(),
                    context.getDownloadFolder());
        }
    }

    @Override
    public String getCommandName() {
        return "select several chapters (separated by comma)";
    }

}
