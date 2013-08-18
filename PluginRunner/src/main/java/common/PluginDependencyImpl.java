package common;

/**
 * @author Matthew Madson
 * @since 8/16/13
 */
class PluginDependencyImpl implements PluginDependency {

    @Override
    public void doSomething() {
        System.out.printf(">> I'm a shared injected dependency: %s%n", this);
    }
}
