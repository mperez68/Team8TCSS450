package edu.uw.tcss450.team8tcss450.ui.contacts;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create Dummy Blogs. Use it for development.
 * In future labs, connect to an API to gain actual Blog Data.
 */
public final class ContactGenerator {

    private static final Contact[] BLOGS;
    public static final int COUNT = 20;


    private static int lastContactId = 0;


    static {
        BLOGS = new Contact[COUNT];
        for (int i = 0; i < BLOGS.length; i++) {
            BLOGS[i] = new Contact("Person " + ++lastContactId, "UserName " + ++lastContactId, "Email " + ++lastContactId,  i <= BLOGS.length / 2);
        }
    }

    public static List<Contact> getBlogList() {
        return Arrays.asList(BLOGS);
    }

    public static Contact[] getBLOGS() {
        return Arrays.copyOf(BLOGS, BLOGS.length);
    }

    private ContactGenerator() { }


}
