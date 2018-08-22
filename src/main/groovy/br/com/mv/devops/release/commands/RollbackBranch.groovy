package br.com.mv.devops.release.commands

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository
import picocli.CommandLine

@CommandLine.Command(name = "RollbackBranch",
        description = "Retorna o repositório local para o estado inicial de acondo com o repositório remoto")
class RollbackBranch extends BaseCommand {

    @CommandLine.Option(names = ["-b", "--target-branch"], description = "Branch alvo para restauração do repositório local")
    String targetBranch = "master"

    @Override
    void recursiveRunCommand(Repository repository) {
        super.recursiveRunCommand(repository)

        Grgit git = Grgit.open(dir: repository.getDirectory().getAbsolutePath())

        logger.info("Entering repository: ${repository.getWorkTree().getName()}")
        logger.info("Repository remotes: ${git.remote.list().collect { it.url }}")

        //Remove Checkout target branch
        if (git.branch.list().find { it.name == targetBranch })
            git.checkout(branch: targetBranch)
        else
            git.checkout(branch: targetBranch, createBranch: true, startPoint: "${parent.remote}/${targetBranch}")

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
        git.reset(mode: 'hard', commit: "${parent.remote}/${targetBranch}")
    }
}