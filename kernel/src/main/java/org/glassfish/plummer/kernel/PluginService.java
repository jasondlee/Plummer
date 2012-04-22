package org.glassfish.plummer.kernel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.plummer.api.NavNodes;
import org.glassfish.plummer.api.Plugin;
import org.glassfish.plummer.api.ViewFragment;

/**
 *
 * @author jasonlee
 */
public class PluginService {

    @Inject
    private Instance<Plugin> plugins;
    private List<PluginMetadata> metadata;
    private static final Set<String> classNames = new HashSet<String>();
    
    /*
    private PluginService() {
        try {
            InitialContext ic = new InitialContext();
            BeanManager beanManager = (BeanManager)ic.lookup("java:comp/BeanManager");
            Set<Bean<?>> beans = beanManager.getBeans(Plugin.class);
            Iterator<Bean<?>> iterator = beans.iterator();
            while(iterator.hasNext()) {
                Bean bean = iterator.next();
                System.out.println(bean.getName());
            }
//            CreationalContext<Plugin> ctx = beanManager.createCreationalContext(bean);
            
        } catch (NamingException ex) {
            Logger.getLogger(PluginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static class LazyHolder {
        static final PluginService pluginService = new PluginService();
    }
    
    public static PluginService getPluginService() {
        return LazyHolder.pluginService;
    }
    */

    public List<PluginMetadata> getPlugins() {
        if (metadata == null) {
            List<PluginMetadata> list = new ArrayList<PluginMetadata>();
            Iterator<Plugin> iter = plugins.iterator();

            while (iter.hasNext()) {
                Plugin cp = iter.next();
                PluginMetadata cpm = new PluginMetadata(cp.getPriority());
                Class clazz = cp.getClass();
                try {
                    processAnnotations(cpm, clazz);
//                    cpm.setPluginPackage(clazz.getPackage().getName());
                } catch (Exception ex) {
                    Logger.getLogger(PluginService.class.getName()).log(Level.SEVERE, null, ex);
                }

                list.add(cpm);
            }
            Collections.sort(list, new PluginComparator());
            metadata = Collections.unmodifiableList(list);
        }

        return metadata;
    }

    protected void processAnnotations(PluginMetadata cp, Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        for (Field field : clazz.getFields()) {
            ViewFragment vf = field.getAnnotation(ViewFragment.class);
            if (vf != null) {
                cp.addViewFragment(vf.type(), (String) field.get(clazz));
            }
            NavNodes nn = field.getAnnotation(NavNodes.class);
            if (nn != null) {
                cp.addNavigationNodes(nn.parent(), (List<NavigationNode>) field.get(clazz));
            }
        }

        for (Method method : clazz.getMethods()) {
            NavNodes nn = method.getAnnotation(NavNodes.class);
            if (nn != null) {
                cp.addNavigationNodes(nn.parent(), (List<NavigationNode>) method.invoke(null, new Object[]{}));
            }
        }
    }

    public void addClass(String className) {
        classNames.add(className);
    }

    public static Set<String> getClassNames() {
        return classNames;
    }
}
