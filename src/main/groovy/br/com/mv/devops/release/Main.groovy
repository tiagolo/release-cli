package br.com.mv.devops.release

import br.com.mv.devops.release.commands.PrepareBranch
import br.com.mv.devops.release.commands.RollbackBranch
import br.com.mv.devops.release.providers.ManifestVersionProvider

import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(name = "release-cli", subcommands = [RollbackBranch.class, PrepareBranch.class], versionProvider = ManifestVersionProvider.class)
class Main implements Callable<Void> {

    @CommandLine.Option(names = ["-V", "--version"], versionHelp = true, description = "display version info")
    boolean versionInfoRequested;

    @CommandLine.Option(names = ["-h", "--help"], usageHelp = true, description = "display this help message")
    boolean usageHelpRequested

    static void main(String[] args) {
        CommandLine.call(new Main(), args)
    }

    @Override
    Void call() throws Exception {
        CommandLine.usage(this, System.out)
    }
}