package br.com.mv.devops.release

import br.com.mv.devops.release.commands.operations.PrepareBranch
import br.com.mv.devops.release.commands.operations.PrepareRelease
import br.com.mv.devops.release.commands.operations.RollbackBranch
import br.com.mv.devops.release.commands.base.AbstractCommand
import br.com.mv.devops.release.providers.ManifestVersionProvider
import picocli.CommandLine

@CommandLine.Command(name = "release-cli",
        subcommands = [RollbackBranch.class, PrepareBranch.class, PrepareRelease.class],
        versionProvider = ManifestVersionProvider.class,
        description = "Command Line Interface for releasing software with git")
class ReleaseCLI extends AbstractCommand {

    @CommandLine.Option(names = ["-v", "--version"], versionHelp = true, description = "display version info")
    boolean versionInfoRequested

    @CommandLine.Option(names = ["-r", "--remote"], description = "Repositório remoto que servirá de referência para rollback local")
    String remote = "origin"

    static void main(String[] args) {
        CommandLine.run(new ReleaseCLI(), args)
    }

    @Override
    void run() throws Exception {
        println """\
  _____      _                                _ _ 
 |  __ \\    | |                              | (_)
 | |__) |___| | ___  __ _ ___  ___ ______ ___| |_ 
 |  _  // _ \\ |/ _ \\/ _` / __|/ _ \\______/ __| | |
 | | \\ \\  __/ |  __/ (_| \\__ \\  __/     | (__| | |
 |_|  \\_\\___|_|\\___|\\__,_|___/\\___|      \\___|_|_|
"""
        CommandLine.usage(this, System.out)
    }
}