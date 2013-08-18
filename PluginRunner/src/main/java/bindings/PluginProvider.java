package bindings;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import plugin.Plugin;

import java.util.List;
import java.util.Set;

import static org.reflections.util.ClasspathHelper.contextClassLoader;
import static org.reflections.util.ClasspathHelper.staticClassLoader;

/**
 * @author Matthew Madson
 * @since 8/16/13
 */
public class PluginProvider {

    private final PluginClassLoaderProvider classLoaderProvider;
    private final Injector appRootInjector;

    @Inject
    public PluginProvider(final Injector appRootInjector, final PluginClassLoaderProvider classLoaderProvider) {
        this.appRootInjector = appRootInjector;
        this.classLoaderProvider = classLoaderProvider;
    }

    public Set<Plugin> getPlugins() {
        final List<ClassLoader> pluginClassLoaders = classLoaderProvider.getPluginClassLoaders();
        ConfigurationBuilder config = new ConfigurationBuilder();
        config.addScanners(new SubTypesScanner());
        config.addClassLoaders(contextClassLoader(), staticClassLoader());
        config.addClassLoaders(pluginClassLoaders);
        try {
            config.addUrls(ClasspathHelper.forClassLoader(contextClassLoader(), staticClassLoader()));
            config.addUrls(ClasspathHelper.forClassLoader(pluginClassLoaders.toArray(new ClassLoader[pluginClassLoaders.size()])));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Set<Class<? extends Plugin>> pluginClasses = new Reflections(config).getSubTypesOf(Plugin.class);
        return appRootInjector.createChildInjector(new PluginMultibinderModule(pluginClasses)).getInstance(PluginMultibinderProvider.class).getPlugins();
    }
}
