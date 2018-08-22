package br.com.mv.devops.release

import br.com.mv.devops.release.commands.BaseCommand
import br.com.mv.devops.release.commands.PrepareBranch
import br.com.mv.devops.release.commands.PrepareRelease
import br.com.mv.devops.release.commands.RollbackBranch
import br.com.mv.devops.release.providers.ManifestVersionProvider
import picocli.CommandLine

@CommandLine.Command(name = "release-cli",
        subcommands = [RollbackBranch.class, PrepareBranch.class, PrepareRelease.class],
        versionProvider = ManifestVersionProvider.class,
        description = "Command Line Interface for releasing software with git")
class ReleaseCLI extends BaseCommand {

    @CommandLine.Option(names = ["-v", "--version"], versionHelp = true, description = "display version info")
    boolean versionInfoRequested

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