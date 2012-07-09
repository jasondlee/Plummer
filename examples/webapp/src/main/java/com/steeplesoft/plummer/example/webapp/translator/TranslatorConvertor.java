/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.webapp.translator;

import com.steeplesoft.plummer.example.model.BlogEntryProcessor;
import com.steeplesoft.plummer.example.model.Translator;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

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
