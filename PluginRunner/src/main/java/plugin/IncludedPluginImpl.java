package plugin;

import com.google.inject.Inject;
import common.PluginDependency;

/**
 * @author Matthew Madson
 * @since 8/16/13
 */
class IncludedPluginImpl implements Plugin {

    private final PluginDependency dep;

    @Inject
    IncludedPluginImpl(final PluginDependency dep) {
        this.dep = dep;
    }

    @Override
    public void doSomething() {
        System.out.printf(">> I'm a plugin included in plugin runner's classpath and I have an injected dependency: %s%n", this);
        dep.doSomething();
    }
}
