package org.glassfish.plummer.kernel.jsf;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.plummer.kernel.PluginService;

/**
 *
 * @author jdlee
 */
public class PluginUtil {
    public static PluginService getPluginService() {
        try {
            InitialContext ic = new InitialContext();
            BeanManager beanManager = (BeanManager)ic.lookup("java:comp/BeanManager");
            AnnotatedType<PluginService> at = beanManager.createAnnotatedType(PluginService.class);
            final Bean<PluginService> bean = (Bean<PluginService>)beanManager.resolve(beanManager.getBeans(at.getBaseType()));
            CreationalContext<PluginService> creationalContext = beanManager.createCreationalContext(bean);
            return bean.create(creationalContext);
        } catch (NamingException ex) {
            Logger.getLogger(PluginUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null; //getHabitat().getComponent(PluginService.class);
    }
}
