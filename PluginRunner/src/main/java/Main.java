import bindings.PluginProvider;
import bindings.PluginProviderModule;
import com.google.inject.Guice;
import plugin.Plugin;

import java.io.IOException;

/**
 * @author Matthew Madson
 * @since 8/16/13
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final PluginProvider pluginProvider = Guice.createInjector(new PluginProviderModule(System.getProperty("plugin.dir", "./plugins"))).getInstance(PluginProvider.class);
        while (true) {
            System.in.read();
            for (final Plugin plugin : pluginProvider.getPlugins()) {
                plugin.doSomething();
            }
        }
    }
}
