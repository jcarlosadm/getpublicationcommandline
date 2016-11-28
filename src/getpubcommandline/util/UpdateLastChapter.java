package getpubcommandline.util;

import java.util.List;

import getpubcommandline.ui.commands.ContextCommand;

public final class UpdateLastChapter {

    private UpdateLastChapter() {
    }

    public static boolean update(ContextCommand context, String lastChapter) {

        if (context.getJsonPublication() == null
                || context.getProject() == null) {
            return false;
        }

        context.getJsonPublication().load();

        String oldLastChapter = context.getJsonPublication()
                .getLastChapter(context.getProject().getName());
        oldLastChapter = (oldLastChapter == null ? "" : oldLastChapter);

        List<String> chapters = context.getJsonPublication()
                .getChapters(context.getProject().getName());
        int indexOldLastChapter = chapters.indexOf(oldLastChapter);
        int indexNewChapter = chapters.indexOf(lastChapter);

        if (indexOldLastChapter == -1 || indexNewChapter == -1) {
            return false;
        }

        if (indexNewChapter > indexOldLastChapter) {
            context.getJsonPublication().setLastChapter(
                    context.getProject().getName(), lastChapter);
            context.getJsonPublication().save();
        }

        return true;
    }

}
