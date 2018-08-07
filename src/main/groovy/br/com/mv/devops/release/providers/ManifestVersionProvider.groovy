package br.com.mv.devops.release.providers

import picocli.CommandLine

import java.util.jar.Attributes
import java.util.jar.Manifest

/**
 * {@link picocli.CommandLine.IVersionProvider} implementation that returns version information from the picocli-x.x.jar file's {@code /META-INF/MANIFEST.MF} file.
 */
class ManifestVersionProvider implements CommandLine.IVersionProvider {
    String[] getVersion() throws Exception {
        Enumeration<URL> resources = CommandLine.class.getClassLoader().getResources("META-INF/MANIFEST.MF")
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            try {
                Manifest manifest = new Manifest(url.openStream())
                if (isApplicableManifest(manifest)) {
                    Attributes attr = manifest.getMainAttributes()
                    return [get(attr, "Implementation-Title") + " version \"" +
                                    get(attr, "Implementation-Version") + "\""]
                }
            } catch (IOException ex) {
                return ["Unable to read from " + url + ": " + ex];
            }
        }
        return new String[0]
    }

    private boolean isApplicableManifest(Manifest manifest) {
        Attributes attributes = manifest.getMainAttributes()
        return "release-cli".equals(get(attributes, "Implementation-Title"))
    }

    private static Object get(Attributes attributes, String key) {
        return attributes.get(new Attributes.Name(key))
    }
}