package bindings;

import com.google.inject.Inject;
import plugin.Plugin;

import java.util.Set;

/**
 * @author Matthew Madson
 * @since 8/16/13
 */
class PluginMultibinderProvider {
    private final Set<Plugin> plugins;

    @Inject
    PluginMultibinderProvider(Set<Plugin> plugins) {
        this.plugins = plugins;
    }

    public Set<Plugin> getPlugins() {
        return plugins;
    }
}
