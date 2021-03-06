package getpubcommandline.ui.commands;

import getpubcommandline.ui.commands.implementations.StoreLastChapter;
import getpublication.folders.DownloadFolder;
import getpublication.json.publication.JsonPublication;
import getpublication.parser.HtmlNewsParser;
import getpublication.parser.HtmlSearchParser;
import getpublication.project.Project;
import getpublication.project.ProjectBuilder;

public class ContextCommand {
    private DownloadFolder downloadFolder = new DownloadFolder();
    
    private JsonPublication jsonPublication = null;
    
    private Project project = null;
    
    private boolean anonymousMode = false;
    
    private ProjectBuilder projectBuilder = null;
    
    private HtmlSearchParser htmlSearchParser = null;
    
    private HtmlNewsParser htmlNewsParser = null;
    
    private StoreLastChapter storeLastChapter = null;
    
    public void setStoreLastChapter(StoreLastChapter storeLastChapter) {
        this.storeLastChapter = storeLastChapter;
    }
    
    public void loadLastChapter() {
        this.storeLastChapter.loadLastChapter(this);
    }

    public HtmlSearchParser getHtmlSearchParser() {
        return this.htmlSearchParser;
    }

    public void setHtmlSearchParser(HtmlSearchParser htmlSearchParser) {
        this.htmlSearchParser = htmlSearchParser;
    }

    public ProjectBuilder getProjectBuilder() {
        return this.projectBuilder;
    }

    public void setProjectBuilder(ProjectBuilder builder) {
        this.projectBuilder = builder;
    }

    public boolean isAnonymousMode() {
        return this.anonymousMode;
    }

    public void setAnonymousMode(boolean anonymousMode) {
        this.anonymousMode = anonymousMode;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setDownloadFolder(DownloadFolder downloadFolder){
        this.downloadFolder = downloadFolder;
    }
    
    public DownloadFolder getDownloadFolder(){
        return this.downloadFolder;
    }

    public JsonPublication getJsonPublication() {
        return this.jsonPublication;
    }
    
    public void setJsonPublication(JsonPublication jsonPublication){
        this.jsonPublication = jsonPublication;
    }

    public HtmlNewsParser getHtmlNewsParser() {
        return htmlNewsParser;
    }

    public void setHtmlNewsParser(HtmlNewsParser htmlNewsParser) {
        this.htmlNewsParser = htmlNewsParser;
    }
}
