package getpubcommandline.ui.commands.implementations;

import java.util.List;
import java.util.Map;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.ConverterImageAlgorithm;
import getpublication.json.publication.JsonPublication;
import getpublication.json.publication.PropertiesName;
import getpublication.parser.HtmlNewsObjectInfo;
import getpublication.parser.HtmlNewsParser;
import getpublication.project.Project;
import getpublication.project.ProjectBuilder;

public class UpdateFavProjects implements Command {

    private static final int TENTATIVES = 10;

	@Override
    public void action(ContextCommand context) {
        JsonPublication jsonPublication = context.getJsonPublication();
        jsonPublication.load();
        List<String> favProjects = jsonPublication.getFavProjects();
        if (favProjects == null || favProjects.isEmpty()) {
            return;
        }
        
        System.out.println("updating favorite projects...");

        HtmlNewsParser htmlNewsParser = context.getHtmlNewsParser();
        Map<String, HtmlNewsObjectInfo> newsResult = htmlNewsParser
                .getNewsListByProject();
        if (newsResult == null || newsResult.isEmpty()) {
            return;
        }

        for (String projectName : favProjects) {
            System.out.println("Updating "+projectName+"...");
            Project project = this.buildProject(projectName, context);
            int count = 0;
            while (project == null && count <= TENTATIVES) {
            	project = this.buildProject(projectName, context);
            	count++;
            }
            
            if (project == null) {
                System.out.println("   failed to get project");
                continue;
            }

            List<String> chapterNames = project.getAllChapterNames();
            
            count = 0;
            while(chapterNames == null && count <= TENTATIVES) {
            	chapterNames = project.getAllChapterNames();
            	count++;
            }
            
            if (chapterNames == null) {
                System.out.println("   failed to get chapters");
                continue;
            }

            List<String> chaptersRecorded = jsonPublication
                    .getChapters(projectName);
            
            String lastChapterRecorded = "";
            if (!chaptersRecorded.isEmpty())
                lastChapterRecorded = chaptersRecorded
                    .get(chaptersRecorded.size() - 1);
            
            if (chapterNames.size() > chaptersRecorded.size()) {
                int index = chapterNames.indexOf(lastChapterRecorded);
                
                for (++index; index < chapterNames.size(); ++index) {
                    jsonPublication.addChapter(projectName,
                            chapterNames.get(index));
                }
            }

            String changedProjectName = projectName.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
            if (newsResult.containsKey(changedProjectName)) {
                List<String> newChapters = newsResult.get(changedProjectName).getChapters();

                for (int index = newChapters.size() - 1; index >= 0; --index) {
                    if (!chapterNames.contains(newChapters.get(index))
                            && !chaptersRecorded.contains(newChapters.get(index))) {
                        jsonPublication.addChapter(projectName,
                                newChapters.get(index));
                    }
                }
            }
        }

        jsonPublication.save();
        
        System.out.println("done!\n");
    }

    private Project buildProject(String projectName, ContextCommand context) {
        ProjectBuilder projectBuilder = context.getProjectBuilder();
        projectBuilder.setName(projectName);
        projectBuilder.setUrlPart(context.getJsonPublication()
                .getProjectProperty(projectName, PropertiesName.NAME_IN_URL));
        projectBuilder.setAnonymousMode(context.isAnonymousMode());
        Project project = projectBuilder.build();
        project.setConvertImageAlgorithm(new ConverterImageAlgorithm());

        return project;
    }

    @Override
    public String getCommandName() {
        return "update favorite projects";
    }

}
