package getpubcommandline.ui.commands.implementations;

import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;

public class ShowChaptersCommand implements Command {

    private static final int ADJUST_SPACE_BETWEEN_COLUMNS = 3;
    private static final int COLUMN_MAX = 6;

    private int biggerChapterNameLength = 0;
    private String lastProject = "";

    @Override
    public void action(ContextCommand context) {
        if (context.getProject() == null) {
            return;
        }

        List<String> chapterNameList = context.getProject()
                .getAllChapterNames();
        if (chapterNameList == null) {
            return;
        }

        if (lastProject.equals("")
                || !context.getProject().getName().equals(this.lastProject)) {
            this.lastProject = context.getProject().getName();
            this.biggerChapterNameLength = 0;
            
            for (String chapterName : chapterNameList) {
                if (chapterName.length() > this.biggerChapterNameLength) {
                    this.biggerChapterNameLength = chapterName.length();
                }
            }
        }

        int columnCounter = 0;

        for (String chapterName : chapterNameList) {

            if (columnCounter < COLUMN_MAX - 1) {
                System.out.print(chapterName + this.getSpaceAfter(chapterName));
                columnCounter += 1;
            } else {
                System.out.println(chapterName);
                columnCounter = 0;
            }
        }

        System.out.println("");
    }

    private String getSpaceAfter(String chapterName) {
        String spaceAfter = "";
        int spaceAfterCount = this.biggerChapterNameLength
                - chapterName.length() + ADJUST_SPACE_BETWEEN_COLUMNS;
        for (int index = 0; index < spaceAfterCount; index++) {
            spaceAfter += " ";
        }
        return spaceAfter;
    }

    @Override
    public String getCommandName() {
        return "show chapters";
    }

}
