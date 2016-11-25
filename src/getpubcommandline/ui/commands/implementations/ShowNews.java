package getpubcommandline.ui.commands.implementations;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.ShowImageWindow;
import getpublication.folders.UserFolder;
import getpublication.parser.HtmlNewsObjectInfo;
import getpublication.util.downloader.Downloader;

public class ShowNews implements Command {

    @Override
    public void action(ContextCommand context) {
        Map<String, HtmlNewsObjectInfo> map = context.getHtmlNewsParser()
                .getNewsListByProject();
        if (map == null || map.isEmpty()) {
            System.out.println("error to get news");
            return;
        }

        Map<String, String> imageMap = new HashMap<>();

        for (String key : map.keySet()) {
            String imageName = map.get(key).getUrlImage();
            imageName = imageName.substring(imageName.lastIndexOf("/") + 1);
            imageMap.put(imageName, map.get(key).getUrlImage());
        }

        Map<String, String> imageLocations = new HashMap<>();

        File folder = new File(UserFolder.getPathToImageCacheFolder());
        for (File file : folder.listFiles()) {
            if (imageMap.containsKey(file.getName())) {
                imageLocations.put(file.getName(), file.getAbsolutePath());
            }
        }

        this.downloadImages(imageMap, imageLocations, folder);

        this.showWindowWithImages(map, imageLocations);
    }

    private void showWindowWithImages(Map<String, HtmlNewsObjectInfo> map,
            Map<String, String> imageLocations) {

        ShowImageWindow sWindow = new ShowImageWindow();
        for (String key : map.keySet()) {
            String imageName = map.get(key).getUrlImage();
            imageName = imageName.substring(imageName.lastIndexOf("/") + 1);

            sWindow.addContent(map.get(key).getProjectName(),
                    map.get(key).getUrlProject(), imageLocations.get(imageName),
                    map.get(key).getChapters(), null);
        }

        sWindow.showHtml("News");

    }

    private void downloadImages(Map<String, String> imageMap,
            Map<String, String> imageLocations, File folder) {

        Downloader downloader = new Downloader();

        for (String imageName : imageMap.keySet()) {
            if (imageLocations.containsKey(imageName)) {
                continue;
            }

            try {
                downloader.setUrl(imageMap.get(imageName));
                downloader.run(new File(
                        folder.getAbsolutePath() + File.separator + imageName));
                imageLocations.put(imageName,
                        folder.getAbsolutePath() + File.separator + imageName);

            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    public String getCommandName() {
        return "show news";
    }

}
