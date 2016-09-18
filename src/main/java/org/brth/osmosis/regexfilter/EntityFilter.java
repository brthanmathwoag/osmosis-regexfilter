package org.brth.osmosis.regexfilter;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;

import java.util.Map;
import java.util.regex.Pattern;

public class EntityFilter {
    private Pattern keyPattern;
    private Pattern valuePattern;

    public EntityFilter(Map.Entry<String, String> entry) {
        this.keyPattern = Pattern.compile(entry.getKey());
        this.valuePattern = Pattern.compile(entry.getValue());
    }

    public boolean match(Entity entity) {
        for(Tag tag : entity.getTags()) {
            if(!keyPattern.matcher(tag.getKey()).find()) {
                continue;
            }
            if(valuePattern.matcher(tag.getValue()).find()) {
                return true;
            }
        }
        return false;
    }
}
