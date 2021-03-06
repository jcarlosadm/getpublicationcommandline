package getpubcommandline.ui.commands.implementations;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.ShowImageWindow;
import getpublication.folders.UserFolder;
import getpublication.parser.HtmlNewsObjectInfo;
import getpublication.parser.HtmlSearchParser;
import getpublication.util.UserInput;
import getpublication.util.downloader.Downloader;

public class SearchCommand implements Command {

    @Override
    public void action(ContextCommand context) {
        HtmlSearchParser htmlSearchParser = context.getHtmlSearchParser();
        if (htmlSearchParser == null) {
            System.out.println("fail to search");
            return;
        }

        System.out.print("type a search term: ");
        String searchTerm = UserInput.getInput();

        Map<String, HtmlNewsObjectInfo> searchResult = htmlSearchParser
                .getSearchResult(searchTerm);
        if (searchResult == null) {
            System.out.println("fail to search");
            return;
        }

        Map<String, String> imageMap = new HashMap<>();

        for (String key : searchResult.keySet()) {
            String imageName = searchResult.get(key).getUrlImage();
            if (imageName == null || imageName.isEmpty()) {
                continue;
            }

            imageName = imageName.substring(imageName.lastIndexOf("/") + 1);
            imageMap.put(imageName, searchResult.get(key).getUrlImage());
        }

        Map<String, String> imageLocations = new HashMap<>();

        File folder = new File(UserFolder.getPathToImageCacheFolder());
        for (File file : folder.listFiles()) {
            if (imageMap.containsKey(file.getName())) {
                imageLocations.put(file.getName(), file.getAbsolutePath());
            }
        }

        this.downloadImages(imageMap, imageLocations, folder);

        this.showWindowWithImages(searchResult, imageLocations);
    }

    private void showWindowWithImages(Map<String, HtmlNewsObjectInfo> map,
            Map<String, String> imageLocations) {

        ShowImageWindow sWindow = new ShowImageWindow();
        for (String key : map.keySet()) {
            String imageName = map.get(key).getUrlImage();
            imageName = imageName.substring(imageName.lastIndexOf("/") + 1);

            sWindow.addContent(map.get(key).getProjectName(),
                    map.get(key).getUrlProject(), imageLocations.get(imageName),
                    null, map.get(key).getDescription());
        }

        sWindow.showHtml("Search");

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
        return "search";
    }

}
