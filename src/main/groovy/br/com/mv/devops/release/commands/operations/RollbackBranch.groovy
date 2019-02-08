package br.com.mv.devops.release.commands.operations

import br.com.mv.devops.release.commands.base.AbstractRecursiveCommand
import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository
import picocli.CommandLine

@CommandLine.Command(name = "RollbackBranch",
        description = "Retorna o repositório local para o estado inicial de acondo com o repositório remoto")
class RollbackBranch extends AbstractRecursiveCommand {

    String getBranch() {
        configuration.workflow.source
    }

    @Override
    void recursiveRunCommand(Repository repository) {
        super.recursiveRunCommand(repository)
        Grgit git = openGit(repository)

        //Remove Checkout target branch
        if (git.branch.list().find { it.name == branch })
            git.checkout(branch: branch)
        else
            git.checkout(branch: branch, createBranch: true, startPoint: "${parent.remote}/${branch}")

        // Remove Branches
        def branchesToRemove = git.branch.list()
                .findAll { it != git.branch.current }
                .collect { it.name }
        if (branchesToRemove)
            git.branch.remove(names: branchesToRemove, force: true)

        // Remove Tags
        def tagsToRemove = git.tag.list()
                .collect { it.name }
        if (tagsToRemove)
            git.tag.remove(names: tagsToRemove)

        git.fetch()
        git.reset(mode: 'hard', commit: "${parent.remote}/${branch}")
        git.clean()
    }
}