package getpubcommandline;

import java.io.File;
import java.util.Collection;

import getpubcommandline.plugin.CommandLineSitePlugin;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.ui.commands.implementations.SiteChooserCommand;
import getpubcommandline.util.pageProgress.PrintStrategyCommandLine;
import getpublication.folders.UserFolder;
import getpublication.util.folder.CreateFolder;
import getpublication.util.pageProgress.PageProgressPrinter;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

public class GetPublicationMain {

    private static final File PLUGIN_FOLDER = new File(
            UserFolder.getPathToPluginFolder() + File.separator
                    + "commandLine");

    public static void main(String[] args) {
        CreateFolder.create(PLUGIN_FOLDER);

        PluginManager pluginManager = PluginManagerFactory
                .createPluginManager();
        pluginManager.addPluginsFrom(PLUGIN_FOLDER.toURI());

        Collection<CommandLineSitePlugin> plugins = new PluginManagerUtil(
                pluginManager).getPlugins(CommandLineSitePlugin.class);

        PageProgressPrinter pageProgressPrinter = PageProgressPrinter
                .getInstance();
        pageProgressPrinter
                .setPrintStrategy(PrintStrategyCommandLine.getInstance());

        ContextCommand contextCommand = new ContextCommand();
        SiteChooserCommand command = new SiteChooserCommand();
        for (CommandLineSitePlugin sitePlugin : plugins) {
            command.addSiteCommand(sitePlugin.getCommand());
        }

        command.action(contextCommand);
    }
}
