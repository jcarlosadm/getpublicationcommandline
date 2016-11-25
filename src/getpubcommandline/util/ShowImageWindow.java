package getpubcommandline.util;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import getpublication.folders.UserFolder;

public class ShowImageWindow {

    private class Content {
        private String title = null;
        private String urlProject = "";
        private String imageLocation = null;
        private List<String> chapters = null;
        private String description = null;
    }

    private class ContentComparator implements Comparator<Content> {
        @Override
        public int compare(Content o1, Content o2) {
            return (o1.title.compareTo(o2.title));
        }
    }

    private List<Content> contents = new ArrayList<>();

    public void addContent(String title, String urlProject,
            String imageLocation, List<String> chapters, String description) {
        Content content = new Content();
        content.chapters = chapters;
        content.title = title;
        content.urlProject = urlProject;
        content.imageLocation = imageLocation;
        content.description = description;
        contents.add(content);
    }

    public void showHtml(String title) {
        Collections.sort(contents, new ContentComparator());

        File file = new File(UserFolder.getPathToTempFolder() + File.separator
                + title.replace(" ", "").trim() + ".html");
        if (file.exists()) {
            file.delete();
        }

        BufferedWriter bWriter = null;
        try {
            bWriter = new BufferedWriter(new FileWriter(file));

            bWriter.write("<!DOCTYPE HTML>" + "<head>"
                    + "<meta charset=\"UTF-8\">" + "<style>"
                    + ".imageContainer{" + "float:left;" + "width: 160px;"
                    + "height: 400px;" + "margin: 10px;" + "padding: 15px;"
                    + "border: 2px;" + "border-color: black;"
                    + "border-style: outset;" + "}" + "h1 {" + "color: maroon;"
                    + "margin-left: 40px;" + "}" + "</style>" + "</head>"
                    + "<body>" + System.lineSeparator() + "<h1>" + title
                    + "</h1>");

            for (Content content : contents) {
                bWriter.write("<div class=\"imageContainer\">"
                        + System.lineSeparator());

                if (content.title != null)
                    this.writeTitle(bWriter, content.title, content.urlProject);
                if (content.chapters != null)
                    this.writeChapters(bWriter, content.chapters);
                if (content.imageLocation != null)
                    this.writeImage(bWriter, content.imageLocation);
                if (content.description != null)
                    this.writeDescription(bWriter, content.description);

                bWriter.write("</div>" + System.lineSeparator());
            }

            bWriter.write("</body>" + System.lineSeparator());

            bWriter.close();

            Desktop.getDesktop().browse(file.toURI());
        } catch (Exception e) {
            System.out.println("error to create html file");
        }
    }

    private void writeDescription(BufferedWriter bWriter, String description)
            throws Exception {
        bWriter.write("<p>" + description + "</p>" + System.lineSeparator());
    }

    private void writeTitle(BufferedWriter bWriter, String title,
            String urlProject) throws Exception {
        bWriter.write("<p><a href=\"" + urlProject + "\">" + title + "</a></p>"
                + System.lineSeparator());
    }

    private void writeChapters(BufferedWriter bWriter, List<String> chapters)
            throws Exception {
        bWriter.write("<p>Chapters ");
        for (String chapter : chapters) {
            bWriter.write(chapter + " ");
        }
        bWriter.write("</p>" + System.lineSeparator());
    }

    private void writeImage(BufferedWriter bWriter, String imageLocation)
            throws Exception {
        bWriter.write(
                "<img src=\"" + imageLocation + "\">" + System.lineSeparator());
    }
}
