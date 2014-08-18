package nl.capaxit.jaxrsamf.bootstrap;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Bootstrap JAX-RS. This class is declared in the web.xml.
 *
 * @author jcraane
 */
public class Application extends ResourceConfig {
    public Application() {
        System.out.println("INIT APPLICATION");
        packages("nl.capaxit.jaxrsamf");
    }
}
