package br.com.mv.devops.release.providers

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.Repository

class GrGitProvider {

    static Repository getLocalRepository() {
        Grgit grgit = Grgit.open(dir: System.getenv("pwd"))
        return grgit.repository.jgit.repository
    }
}
