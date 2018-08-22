package br.com.mv.devops.release.models

class Configuration {

    String[] files
    Workflow[] workflows
}

class Workflow {
    String source
    String[] merge
    String[] conditionalMerge
}