package common;

import com.google.inject.PrivateModule;

/**
 * @author Matthew Madson
 * @since 8/17/13
 */
public class CommonPluginDependenciesModule extends PrivateModule {
    @Override
    protected void configure() {
        bind(PluginDependency.class).to(PluginDependencyImpl.class).asEagerSingleton();
        expose(PluginDependency.class);
    }
}
