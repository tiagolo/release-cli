package br.com.mv.devops.release.models

import groovy.io.FileType
import groovy.text.SimpleTemplateEngine
import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Configuration {

    String version
    Map<String, String> files
    Map<String,Workflow> workflows

    protected Logger logger
    String workflowName

    Configuration() {
        logger = LoggerFactory.getLogger("ReleaseCLI - Configuration")
    }

    //=======================
    // Getters
    //=======================

    Workflow getWorkflow() {
        if (workflows[workflowName]) {
            workflows[workflowName]
        } else {
            logger.error("O workflow ${workflowName.toUpperCase()} não existe na configuração de release do projeto")
            System.exit(1)
        }
    }

    String getCleanVersion(){
        return version - "-SNAPSHOT"
    }

    //=======================
    // Actions
    //=======================

    Configuration init(String workflowName) {
        this.workflowName = workflowName
        interpolateProperties()
    }

    void interpolateProperties() {
        SimpleTemplateEngine engine = new groovy.text.SimpleTemplateEngine()
        Map binding = ["version": cleanVersion ]

        workflows.each { name, Workflow workflow ->
            if (workflow.target) workflow.target = engine.createTemplate(workflow.target).make(binding).toString()
        }

        files.each { file, pattern ->
            files[file] = pattern =  engine.createTemplate(pattern).make(binding).toString()
        }
    }

    void updateFiles(Repository repository, Grgit git, Workflow workflow) {
        files.each { name, pattern ->
            repository.workTree.eachFileRecurse(FileType.FILES) { file ->
                if(file.name == name) {
                    println file.text.replaceAll(pattern,version)
                }
            }
        }
    }

}