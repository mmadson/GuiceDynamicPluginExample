package bindings;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * @author Matthew Madson
 * @since 8/17/13
 */
class PluginClassLoaderProvider {

    private static final Logger LOG = Logger.getLogger(PluginClassLoaderProvider.class.getName());
    private final Map<String, ClassLoader> loadedPluginJars = Maps.newHashMap();
    private final File pluginDir;

    @Inject
    PluginClassLoaderProvider(final @Named("plugin.dir") String pluginDir) {
        this.pluginDir = ensurePluginDirExists(pluginDir);
    }

    private static ClassLoader toClassLoader(final String pathToPluginJar) throws Exception {
        JarFile jarFile = new JarFile(pathToPluginJar);
        Enumeration e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + pathToPluginJar + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            cl.loadClass(className);
        }
        return cl;
    }

    private static File ensurePluginDirExists(final String pluginDir) {
        final File result;
        try {
            result = new File(pluginDir).getCanonicalFile();
            FileUtils.forceMkdir(result);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
        return result;
    }

    public List<ClassLoader> getPluginClassLoaders() {
        try {
            LOG.info(String.format("Scanning %s for plugin jars.", pluginDir));
            for (final File pluginJar : Lists.newArrayList(FileUtils.iterateFiles(pluginDir, new String[]{"jar"}, false))) {
                final String pluginJarPath = pluginJar.getCanonicalPath();
                if (!loadedPluginJars.containsKey(pluginJarPath)) {
                    LOG.info(String.format("Loading: %s", pluginJarPath));
                    loadedPluginJars.put(pluginJarPath, toClassLoader(pluginJarPath));
                }
            }
            return Lists.newArrayList(loadedPluginJars.values());
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
