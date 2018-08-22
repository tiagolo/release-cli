package br.com.mv.devops.release.commands

import br.com.mv.devops.release.ReleaseCLI
import br.com.mv.devops.release.providers.GrGitProvider
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.submodule.SubmoduleWalk
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.yaml.snakeyaml.Yaml
import picocli.CommandLine

@CommandLine.Command(
        headerHeading = "%n",
        descriptionHeading = "%n",
        parameterListHeading = "%nParameters:%n",
        optionListHeading = "%nOptions:%n",
        commandListHeading = "%nCommands:%n")
abstract class BaseCommand implements Runnable {

    @CommandLine.Option(names = ["-h", "--help"], usageHelp = true, description = "display this help message")
    boolean isUsageHelpRequested

    @CommandLine.Option(names = ["-r", "--remote"], description = "Repositório remoto que servirá de referência para rollback local")
    String remote = "origin"

    @CommandLine.ParentCommand
    ReleaseCLI parent

    protected Logger logger

    @Override
    void run() {
        logger = LoggerFactory.getLogger("ReleaseCLI")

        logger.info("""
=========================================
  Step: ${this.class.getAnnotation(CommandLine.Command).name()}
=========================================
""")

        recursiveRunCommand(GrGitProvider.localRepository)
    }

    void recursiveRunCommand(Repository repository) {
        SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)
        while (walk.next()) {
            recursiveRunCommand(walk.repository)
        }

        Yaml yaml = new Yaml()
        Map map = yaml.load(new File(repository.workTree, ".release.yml").newInputStream())
        print map
    }
}
