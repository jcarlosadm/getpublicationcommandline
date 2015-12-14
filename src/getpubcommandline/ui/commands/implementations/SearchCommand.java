package getpubcommandline.ui.commands.implementations;

import java.util.Map;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.parser.HtmlSearchParser;
import getpublication.util.UserInput;

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

        Map<String, String> searchResult = htmlSearchParser
                .getSearchResult(searchTerm);
        if (searchResult == null) {
            System.out.println("fail to search");
            return;
        }

        System.out.println("results:");
        for (String key : searchResult.keySet()) {
            System.out.println("project name:\"" + key + "\" url part:\""
                    + searchResult.get(key) + "\"");
        }
        System.out.println();
    }

    @Override
    public String getCommandName() {
        return "search";
    }

}
