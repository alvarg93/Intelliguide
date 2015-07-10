package com.superevilmegateam.intelliguide.database.export;

import android.graphics.Bitmap;

import com.superevilmegateam.intelliguide.database.items.Category;

import java.util.List;

/**
 * Class used to export Category to xml.
 *
 * @author Jan Badura
 */
public class CategoryToXml {

    public String categoriesToXml(List<Category> categories){
        StringBuilder sb = new StringBuilder("");

        if(categories.size() == 0){
            return sb.toString();
        }

        sb.append("<categories>\n");
        for(Category cat : categories){
            Bitmap bm = cat.getIconBmp();
            sb.append("  <categgory>\n");
            sb.append("    <name>"+cat.getName()+"</name>\n");
            sb.append("  </categgory>\n");
        }
        sb.append("</categories>\n");

        return sb.toString();
    }

}
