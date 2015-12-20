package getpubcommandline.ui.commands.implementations;

import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.util.UserInput;

public class ChapterRangeChooserCommand implements Command {

    private static final int DOWNLOAD_TENTATIVES = 50;

    @Override
    public void action(ContextCommand context) {
        if (context.getProject() == null) {
            return;
        }
        
        List<String> chapterNameList = context.getProject().getAllChapterNames();
        if (chapterNameList == null) {
            return;
        }

        String selectedChapter1 = "", selectedChapter2 = "";

        boolean exit = false;
        while (!exit) {
            selectedChapter1 = this.preOperationTypeChapter();
            if (selectedChapter1.equals("_exit_")) {
                System.out.println("canceled operation");
                return;
            }

            exit = this.operationTypeChapter(context, selectedChapter1, exit, chapterNameList);
        }

        exit = false;
        while (!exit) {
            selectedChapter2 = this.preOperationTypeChapter();
            if (selectedChapter2.equals("_exit_")) {
                System.out.println("canceled operation");
                return;
            }

            exit = this.operationTypeChapter(context, selectedChapter2, exit, chapterNameList);
        }

        int index1 = chapterNameList
                .indexOf(selectedChapter1);
        int index2 = chapterNameList
                .indexOf(selectedChapter2);

        if (index1 < index2) {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }

        for (; index1 >= index2; index1--) {
            String selectedChapter = chapterNameList
                    .get(index1);
            
            int tryCounts = 0;
            while (tryCounts < DOWNLOAD_TENTATIVES) {
                if (context.getProject().downloadChapter(selectedChapter,
                        context.getDownloadFolder()) == true) {
                    break;
                }
                tryCounts++;
            }
        }
    }

    private String preOperationTypeChapter() {
        String selectedChapter1;
        System.out.print("type an existing chapter (or _exit_ to exit): ");
        selectedChapter1 = UserInput.getInput();
        return selectedChapter1;
    }

    private boolean operationTypeChapter(ContextCommand context,
            String selectedChapter, boolean exit, List<String> chapterNameList) {

        if (!chapterNameList
                .contains(selectedChapter)) {
            System.out.println("chapter " + selectedChapter
                    + " not found in database. try again.");
        } else {
            System.out.println("chapter " + selectedChapter + " selected");
            exit = true;
        }
        return exit;
    }

    @Override
    public String getCommandName() {
        return "select a range of chapters";
    }

}
