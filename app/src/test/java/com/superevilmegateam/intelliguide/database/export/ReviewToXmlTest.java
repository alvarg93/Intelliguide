package com.superevilmegateam.intelliguide.database.export;

import com.superevilmegateam.intelliguide.database.items.Review;

import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;


public class ReviewToXmlTest extends TestCase {


    public void testReviewsToXml() throws Exception {
        Review rev = mock(Review.class);
        when(rev.getContent()).thenReturn("Test");
        when(rev.getStars()).thenReturn(1);

        List<Review> list = new LinkedList<Review>();
        list.add(rev);

        String result = new ReviewToXml().reviewsToXml(list);

        String expected = "<reviews>\n" +
                "  <review>\n" +
                "    <text>Test</text>\n" +
                "     <star>1</star>\n" +
                "  </review>\n" +
                "</reviews>\n";
        assertEquals(expected, result);
    }

    public void testReviewToXm2() throws Exception {
        List<Review> list = new LinkedList<Review>();
        String result = new ReviewToXml().reviewsToXml(list);
        String expected = "";
        assertEquals(expected,result);
    }

}