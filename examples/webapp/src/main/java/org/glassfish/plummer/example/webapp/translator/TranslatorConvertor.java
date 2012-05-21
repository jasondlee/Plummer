/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.webapp.translator;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.glassfish.plummer.example.model.BlogEntryProcessor;
import org.glassfish.plummer.example.model.Translator;

/**
 *
 * @author jdlee
 */
@Model
public class TranslatorConvertor implements Converter {
    @Inject @Translator
    Instance<BlogEntryProcessor> processors;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (BlogEntryProcessor t : processors) {
            if (t.getName().equals(value)) {
                return t;
            }
        }
        
        throw new RuntimeException ("Unsupported language: " + value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof BlogEntryProcessor) {
            return ((BlogEntryProcessor)value).getName();
        } else {
            throw new RuntimeException ("TranslatorConverter expects instance of Translator. Found" +
                    value.getClass().getName());
        }
    }
    
}
