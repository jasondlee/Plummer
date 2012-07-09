/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.plummer.example.model;

/**
 *
 * @author jdlee
 */
public class BlogPostedEvent {
    private String blogEntry;

    public BlogPostedEvent(String blogEntry) {
        this.blogEntry = blogEntry;
    }

    public String getBlogEntry() {
        return blogEntry;
    }
}
