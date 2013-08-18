package bindings;

import com.google.inject.PrivateModule;
import com.google.inject.name.Names;
import common.CommonPluginDependenciesModule;

/**
 * @author Matthew Madson
 * @since 8/16/13
 */
public class PluginProviderModule extends PrivateModule {

    private final String pluginDirectory;

    public PluginProviderModule(final String pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }

    @Override
    protected void configure() {
        install(new CommonPluginDependenciesModule());

        bind(String.class).annotatedWith(Names.named("plugin.dir")).toInstance(pluginDirectory);
        bind(PluginClassLoaderProvider.class);
        bind(PluginProvider.class);

        expose(PluginProvider.class);
    }
}
