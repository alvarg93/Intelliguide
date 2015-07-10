package com.superevilmegateam.intelliguide.database.export;

import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.database.items.Review;

import java.util.List;

/**
 * Class used to export Place to xml.
 *
 * @author Jan Badura
 */public class PlaceToXml {

    public String placesToXml(List<Place> places){
        StringBuilder sb = new StringBuilder("");

        if(places.size() == 0){
            return sb.toString();
        }

        sb.append("<places>\n");
        for(Place pl : places) {
            sb.append("  <place>\n");
            sb.append("    <name>" + pl.getName() + "</name>\n");
            sb.append("    <description>" + pl.getDescription() + "</description>\n");
            sb.append("    <verified>" + pl.isVerified() + "</verified>\n");
            sb.append("    <reviews>\n");
            List<Review> reviews = pl.getReviews();
            for (Review review : reviews) {
                sb.append("      <review>\n");
                sb.append("        <text>" + review.getContent() + "</text>\n");
                sb.append("        <star>" + review.getStars() + "</star>\n");
                sb.append("      <review\n>");
            }

            sb.append("    </reviews>\n");
        }
        sb.append("  </place>\n");
        sb.append("</places>\n");

        return sb.toString();
    }
}
