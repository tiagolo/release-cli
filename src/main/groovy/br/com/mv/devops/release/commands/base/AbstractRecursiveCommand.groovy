package br.com.mv.devops.release.commands.base

import br.com.mv.devops.release.models.Configuration
import br.com.mv.devops.release.providers.GrGitProvider
import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.submodule.SubmoduleWalk
import org.yaml.snakeyaml.Yaml
import picocli.CommandLine

abstract class AbstractRecursiveCommand extends AbstractCommand {

    @CommandLine.Parameters(index = "0", paramLabel = "WORKFLOW", description = "execution workflowName")
    String workflowName

    protected Configuration configuration

    @Override
    void run() {
        super.run()

        logger.info("=========================================")
        logger.info("   Step: ${this.class.getAnnotation(CommandLine.Command).name()}")
        logger.info("=========================================\n")

        try {
            Yaml yaml = new Yaml()
            configuration = yaml.loadAs(new File(GrGitProvider.localRepository.workTree, ".release.yml").newInputStream(),
                    Configuration)
            configuration.init(workflowName)

        } catch (FileNotFoundException e) {
            logger.error("Não foi possível encontrar o arquivo de configuração .release.yml")
            System.exit(1)
        }

        recursiveRunCommand(GrGitProvider.localRepository)
    }

    void recursiveRunCommand(Repository repository) {
        SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)
        while (walk.next()) {
            recursiveRunCommand(walk.repository)
        }
    }

    protected Grgit openGit(Repository repository) {
        Grgit git = Grgit.open(dir: repository.getDirectory().getAbsolutePath())

        logger.info("Entering repository: ${repository.getWorkTree().getName()}")
        logger.info("Repository remotes: ${git.remote.list().collect { it.url }}")

        return git
    }
}
