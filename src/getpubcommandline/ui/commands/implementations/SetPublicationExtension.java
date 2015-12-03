package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.StringChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.json.publication.JsonPublication;
import getpublication.util.joinfiles.PublicationExtension;

public class SetPublicationExtension implements Command {

    private static final String DEFAULT_EXTENSION = PublicationExtension.CBZ
            .toString();

    @Override
    public void action(ContextCommand context) {
        JsonPublication json = context.getJsonPublication();
        if (json == null) {
            return;
        }
        json.load();

        String extension = json.getPublicationExtension();
        if (extension == null || extension.equals("")) {
            extension = DEFAULT_EXTENSION;
        }
        
        System.out.println("current extension: "+extension);
        
        StringChooser chooser = new StringChooser();
        chooser.setTitle("Select an extension:");
        for (PublicationExtension pExtension : PublicationExtension.values()) {
            chooser.addString(pExtension.toString());
        }
        
        String newExtension = chooser.run();
        if (newExtension.equals("")) {
            return;
        }
        
        json.setPublicationExtension(newExtension);
        json.save();
        
        System.out.println("selected extension: "+newExtension);
    }

    @Override
    public String getCommandName() {
        return "set extension to final file";
    }

}
