package bindings;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import plugin.Plugin;

import java.util.Set;

/**
 * @author Matthew Madson
 * @since 8/16/13
 */
class PluginMultibinderModule extends AbstractModule {

    private final Set<Class<? extends Plugin>> pluginClassesToBind;

    PluginMultibinderModule(final Set<Class<? extends Plugin>> pluginClasses) {
        pluginClassesToBind = pluginClasses;
    }

    @Override
    protected void configure() {
        final Multibinder<Plugin> pluginMultibinder = Multibinder.newSetBinder(binder(), Plugin.class);
        for (final Class<? extends Plugin> pluginClass : pluginClassesToBind) {
            pluginMultibinder.addBinding().to(pluginClass);
        }
        bind(PluginMultibinderProvider.class);
    }
}
