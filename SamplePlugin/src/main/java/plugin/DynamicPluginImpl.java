package plugin;

import com.google.inject.Inject;
import common.PluginDependency;

/**
 * @author Matthew Madson
 * @since 8/17/13
 */
class DynamicPluginImpl implements Plugin {

    private final PluginDependency dep;

    @Inject
    DynamicPluginImpl(PluginDependency dep) {
        this.dep = dep;
    }

    @Override
    public void doSomething() {
        System.out.printf(">> I'm a dynamic plugin loaded at runtime and I also have an injected dependency: %s%n", this);
        dep.doSomething();
    }
}
