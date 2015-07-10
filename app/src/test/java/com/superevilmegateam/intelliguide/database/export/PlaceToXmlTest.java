package com.superevilmegateam.intelliguide.database.export;

import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.database.items.Review;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;


public class PlaceToXmlTest extends TestCase {

    public void testPlacesToXml() throws Exception {
        Place pl = mock(Place.class);
        when(pl.getName()).thenReturn("Nazwa");
        when(pl.getDescription()).thenReturn("Opis");
        when(pl.isVerified()).thenReturn(true);
        when(pl.getReviews()).thenReturn(new ArrayList<Review>());

        List<Place> list = new LinkedList<Place>();
        list.add(pl);

        String result = new PlaceToXml().placesToXml(list);
        String expected = "<places>\n" +
                "  <place>\n" +
                "    <name>Nazwa</name>\n" +
                "    <description>Opis</description>\n" +
                "    <verified>true</verified>\n" +
                "    <reviews>\n" +
                "    </reviews>\n" +
                "  </place>\n" +
                "</places>\n";

        assertEquals(expected, result);
    }
    public void testPlacesToXml2() throws Exception {
        List<Place> list = new LinkedList<Place>();
        String result = new PlaceToXml().placesToXml(list);
        String expected = "";
        assertEquals(expected,result);
    }


}