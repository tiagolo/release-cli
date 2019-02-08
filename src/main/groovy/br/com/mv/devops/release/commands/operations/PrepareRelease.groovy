package br.com.mv.devops.release.commands.operations

import br.com.mv.devops.release.commands.base.AbstractRecursiveCommand
import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository
import picocli.CommandLine

@CommandLine.Command(name = "PrepareRelease", description = "Change required files for versioning")
class PrepareRelease extends AbstractRecursiveCommand {

    @Override
    void recursiveRunCommand(Repository repository) {
        super.recursiveRunCommand(repository)
        Grgit git = openGit(repository)

        println configuration.updateFiles(repository, workflow)
    }
}
