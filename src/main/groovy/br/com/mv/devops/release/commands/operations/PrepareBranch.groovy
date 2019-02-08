package br.com.mv.devops.release.commands.operations

import br.com.mv.devops.release.commands.base.AbstractRecursiveCommand
import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.operation.BranchAddOp
import org.eclipse.jgit.lib.Repository
import picocli.CommandLine

@CommandLine.Command(name = "PrepareBranch", description = "Cria as branches para o workflowName")
class PrepareBranch extends AbstractRecursiveCommand {

    @Override
    void recursiveRunCommand(Repository repository) {
        super.recursiveRunCommand(repository)
        Grgit git = openGit(repository)

        git.branch.add(name: configuration.workflow.target,
                startPoint: configuration.workflow.source,
                mode: BranchAddOp.Mode.TRACK)

        git.checkout(branch: configuration.workflow.source)
        configuration.updateFiles(repository, git)

        git.checkout(branch: configuration.workflow.target)
        configuration.updateFiles(repository, git)
    }
}