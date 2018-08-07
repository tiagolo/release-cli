package br.com.mv.devops.release.commands

import groovy.json.JsonOutput
import org.ajoberstar.grgit.Grgit
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(name = "PrepareBranch", description = "Cria as branches para o workflow")
class PrepareBranch implements Callable<Void> {


    @CommandLine.Option(names = ["-h", "--help"], usageHelp = true, description = "display this help message")
    boolean isUsageHelpRequested

    @Override
    Void call() throws Exception {
        println Grgit.open( dir: System.getenv("pwd")).status().toString()
//        println JsonOutput.prettyPrint(JsonOutput.toJson(System.getenv()))
        return null
    }
}
