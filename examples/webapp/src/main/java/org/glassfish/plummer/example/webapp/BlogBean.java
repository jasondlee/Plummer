/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.plummer.example.webapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.plummer.example.model.BlogEntryProcessor;
import org.glassfish.plummer.example.model.BlogPostedEvent;
import org.glassfish.plummer.example.model.Tag;
import org.glassfish.plummer.example.model.Translator;

/**
 *
 * @author jdlee
 */
@SessionScoped
@Named
public class BlogBean implements Serializable {
    private String entry;
    private List<String> entries = new ArrayList<String>();

    @Inject @English
    private BlogEntryProcessor translator;
    
    @Inject @Translator
    Instance<BlogEntryProcessor> translators;

    @Inject @Tag
    Instance<BlogEntryProcessor> tags;

    @Inject
    private Event<BlogPostedEvent> blogPostedEvents;
    
    @PostConstruct
    protected void defaults() {
        entries.add("Blog entry #1");
        entries.add("Blog entry #2");
        entries.add("Blog entry #3");
        entries.add("Blog entry #4");
        entries.add("Blog entry #5");
    }
    
    public List<String> getEntries() {
        List<String> list = new ArrayList<String>();
        for (String text : entries) {
            for (BlogEntryProcessor tag : tags) {
                text = tag.process(text);
            }
            text = translator.process(text);
            list.add(text);
        }
        return list;
    }
    
    public String addEntry() {
        entries.add(entry);
        blogPostedEvents.fire(new BlogPostedEvent(entry));
        entry = null;
        return null;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
    
    public List<BlogEntryProcessor> getTranslators() {
        List<BlogEntryProcessor> list = new ArrayList<BlogEntryProcessor>();
        for (BlogEntryProcessor t : translators) {
            list.add(t);
        }
        return list;
    }

    public BlogEntryProcessor getTranslator() {
        return translator;
    }

    public void setTranslator(BlogEntryProcessor translator) {
        this.translator = translator;
    }
}
