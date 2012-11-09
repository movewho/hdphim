package com.hd.phim.data.sectionlist;

import com.hd.phim.data.adapter.CatMovie;

/**
 * Item definition including the section.
 */
public class SectionListItem {
    public CatMovie item;
    public String section;

    public SectionListItem(final CatMovie item, final String section) {
        super();
        this.item = item;
        this.section = section;
    }

    @Override
    public String toString() {
        return item.toString();
    }

    public CatMovie getCatObject(){
    	return item;
    }
}
