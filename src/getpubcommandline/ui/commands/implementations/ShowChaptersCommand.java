package getpubcommandline.ui.commands.implementations;

import java.util.List;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.ColorTerminal;

public class ShowChaptersCommand implements Command {

    private static final int ADJUST_SPACE_BETWEEN_COLUMNS = 3;
    private static final int COLUMN_MAX = 6;

    private int biggerChapterNameLength = 0;
    private String lastProject = "";

    private ColorTerminal c = new ColorTerminal();

    @Override
    public void action(ContextCommand context) {
        if (context.getProject() == null) {
            return;
        }

        context.getJsonPublication().load();

        List<String> chapterNameList = context.getJsonPublication()
                .getChapters(context.getProject().getName());
        if (chapterNameList == null) {
            return;
        }

        String lastChapter = context.getJsonPublication()
                .getLastChapter(context.getProject().getName());
        int indexToBlueColor = chapterNameList.indexOf(lastChapter);

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

        for (int index = 0; index < chapterNameList.size(); ++index) {

            String chapterName = chapterNameList.get(index);
            boolean changeColor = (index > indexToBlueColor ? true : false);

            if (columnCounter < COLUMN_MAX - 1) {
                System.out
                        .print(this.getStringWithColor(chapterName, changeColor)
                                + this.getSpaceAfter(chapterName));
                columnCounter += 1;
            } else {
                System.out.println(
                        this.getStringWithColor(chapterName, changeColor));
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

    private String getStringWithColor(String string, boolean changeColor) {

        return (changeColor ? c.getBlue() + string + c.getReset() : string);
    }

    @Override
    public String getCommandName() {
        return "show chapters";
    }

}
