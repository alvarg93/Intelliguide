package com.superevilmegateam.intelliguide.database.export;

import com.superevilmegateam.intelliguide.database.items.Review;

import java.util.List;

/**
 * Class used to export Review to xml.
 *
 * @author Jan Badura
 */
public class ReviewToXml {

    public String reviewsToXml(List<Review> reviews){
        StringBuilder sb = new StringBuilder("");

        if(reviews.size() == 0){
            return sb.toString();
        }

        sb.append("<reviews>\n");
        for(Review review : reviews) {
            sb.append("  <review>\n");
            sb.append("    <text>" + review.getContent() + "</text>\n");
            sb.append("     <star>" + review.getStars() + "</star>\n");
            sb.append("  </review>\n");
        }
        sb.append("</reviews>\n");

        return sb.toString();
    }
}
