package org.glassfish.plummer.kernel.jsf;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.facelets.impl.DefaultFaceletFactory;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.view.facelets.Facelet;
import org.glassfish.plummer.kernel.PluginMetadata;

/**
 *
 * @author jasonlee
 */
@FacesComponent(ViewFragmentComponent.COMPONENT_TYPE)
public class ViewFragmentComponent extends UIOutput implements SystemEventListener, NamingContainer {

    public static final String COMPONENT_FAMILY = "org.glassfish.plummer.kernel.jsf.ViewFragmentComponent";
    public static final String COMPONENT_TYPE = COMPONENT_FAMILY;
    public static final String RENDERER_TYPE = COMPONENT_FAMILY;
    private String _type;
    private Object[] _state = null;

    public ViewFragmentComponent() {
        super();
        setRendererType(RENDERER_TYPE);
        FacesContext.getCurrentInstance().getViewRoot().subscribeToViewEvent(PostAddToViewEvent.class, this);
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof UIViewRoot);
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;

        for (PluginMetadata cpm : PluginUtil.getPluginService().getPlugins()) {
            final List<String> viewIds = cpm.getViewFragments(getType());
            if (viewIds != null) {
                for (String viewId : viewIds) {
                    UINamingContainer wrapper = new UINamingContainer();
                    wrapper.setId("_vfwrapper" + count++);
                    try {
                        processViewFragment(viewId, context, wrapper);
                        getChildren().add(wrapper);
                        wrapper.setParent(this);
                    } catch (IOException ex) {
                        Logger.getLogger(ViewFragmentComponent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    protected void processViewFragment(String viewId, FacesContext ctx, UIComponent parent) throws IOException {
        ApplicationAssociate associate = ApplicationAssociate.getInstance(ctx.getExternalContext());
        DefaultFaceletFactory faceletFactory = (DefaultFaceletFactory)associate.getFaceletFactory();
        Facelet f = faceletFactory.getFacelet(
//                getClass().getResource(viewId)
                faceletFactory.getResourceResolver().resolveUrl(viewId)
        );

        f.apply(ctx, parent);
    }

    public String getType() {
        if (null != this._type) {
            return this._type;
        }
        ValueExpression _ve = getValueExpression("type");
        return (_ve != null) ? (String) _ve.getValue(getFacesContext().getELContext()) : null;
    }

    public void setType(String type) {
        this._type = type;
    }

    @Override
    public void restoreState(FacesContext _context, Object _state) {
        this._state = (Object[]) _state;
        super.restoreState(_context, this._state[0]);
        _type = (String) this._state[1];
    }

    @Override
    public Object saveState(FacesContext _context) {
        if (_state == null) {
            _state = new Object[2];
        }
        _state[0] = super.saveState(_context);
        _state[1] = _type;

        return _state;
    }
}