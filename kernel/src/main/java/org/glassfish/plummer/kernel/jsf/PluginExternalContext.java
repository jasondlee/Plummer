package org.glassfish.plummer.kernel.jsf;

import java.net.MalformedURLException;
import java.net.URL;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;

/**
 *
 * @author jdlee
 */
public class PluginExternalContext extends ExternalContextWrapper {
    private ExternalContext wrapped;

    public PluginExternalContext() {
        
    }
    
    public PluginExternalContext(ExternalContext wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
         URL url = Thread.currentThread().getContextClassLoader()
                .getResource(path.startsWith("/") ? path.substring(1) : path);
        if (url == null) {
            url = getWrapped().getResource(path);
        }

        return url;
    }

    @Override
    public ExternalContext getWrapped() {
        return wrapped;
    }
}