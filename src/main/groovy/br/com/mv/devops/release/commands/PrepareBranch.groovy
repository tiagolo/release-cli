package br.com.mv.devops.release.commands

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository
import picocli.CommandLine

@CommandLine.Command(name = "PrepareBranch", description = "Cria as branches para o workflow")
class PrepareBranch extends BaseCommand {

    @CommandLine.Option(names = ["-s", "--source-branch"])
    String sourceBranch

    @Override
    void recursiveRunCommand(Repository repository) {
        super.recursiveRunCommand(repository)

        Grgit git = Grgit.open(dir: repository.getDirectory().getAbsolutePath())
    }
}