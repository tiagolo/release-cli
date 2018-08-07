package br.com.mv.devops.release.commands

import org.ajoberstar.grgit.Branch
import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.submodule.SubmoduleWalk
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(name = "RollbackBranch",
        description = "Retorna o repositório local para o estado inicial de acondo com o repositório remoto")
class RollbackBranch implements Runnable {

    @CommandLine.Option(names = ["b", "target-branch"], description = "")
    String targetBranch = "master"

    @CommandLine.Option(names = ["r", "target-remote"], description = "")
    String targetRemote = "origin"

    @Override
    void run() throws Exception {
        Grgit grgit = Grgit.open(dir: System.getenv("pwd"))
        Repository repository = grgit.repository.jgit.repository

        rollbackBranch(repository)
    }

    void rollbackBranch(Repository repository) {
        SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)
        while (walk.next()) {
            rollbackBranch(walk.repository)
        }

        //Remove Checkout target branch
        Grgit git = Grgit.open(dir: repository.getDirectory().getAbsolutePath())
        if(git.branch.list().find { it.name == targetBranch})
            git.checkout(branch: targetBranch)
        else
            git.checkout(branch: targetBranch, createBranch: true, startPoint: "${targetRemote}/${targetBranch}")

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
            git.tag.remove (names: tagsToRemove)

        git.fetch()
        git.reset(mode: 'hard', commit: "${targetRemote}/${targetBranch}")
    }
}