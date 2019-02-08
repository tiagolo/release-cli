package br.com.mv.devops.release.commands.base

import br.com.mv.devops.release.ReleaseCLI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.CommandLine

@CommandLine.Command(
        headerHeading = "%n",
        descriptionHeading = "%n",
        parameterListHeading = "%nParameters:%n",
        optionListHeading = "%nOptions:%n",
        commandListHeading = "%nCommands:%n")
abstract class AbstractCommand implements Runnable {

    @CommandLine.Option(names = ["-h", "--help"], usageHelp = true, description = "display this help message")
    boolean isUsageHelpRequested

    @CommandLine.ParentCommand
    ReleaseCLI parent

    protected Logger logger

    @Override
    void run() {
        logger = LoggerFactory.getLogger("ReleaseCLI")
    }

}
