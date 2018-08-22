package br.com.mv.devops.release.commands

import org.eclipse.jgit.lib.Repository
import picocli.CommandLine

@CommandLine.Command(name = "PrepareRelease", description = "Change required files for versioning")
class PrepareRelease extends BaseCommand {

    @Override
    void recursiveRunCommand(Repository repository) {
        super.recursiveRunCommand(repository)
    }
}
