package br.com.mv.devops.release.models

import br.com.mv.devops.release.models.enums.VersionStrategy

class Workflow {
    String name
    String source
    String target
    String[] merge
    String[] conditionalMerge
    Boolean startReleaseCandidate
    VersionStrategy versionStrategy
}
