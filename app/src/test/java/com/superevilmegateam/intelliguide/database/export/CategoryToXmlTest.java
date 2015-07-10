package com.superevilmegateam.intelliguide.database.export;

import com.superevilmegateam.intelliguide.database.items.Category;

import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;


public class CategoryToXmlTest extends TestCase {

    public void testCategoriesToXml() throws Exception {

        Category cat = mock(Category.class);
        when(cat.getName()).thenReturn("Test");

        List<Category> list = new LinkedList<Category>();
        list.add(cat);

        String result = new CategoryToXml().categoriesToXml(list);

        String expected = "<categories>\n  <categgory>\n    <name>Test</name>\n  </categgory>\n</categories>\n";
        assertEquals(expected, result);
    }

    public void testCategoriesToXml2() throws Exception {
        List<Category> list = new LinkedList<Category>();
        String result = new CategoryToXml().categoriesToXml(list);
        String expected = "";
        assertEquals(expected,result);
    }


}