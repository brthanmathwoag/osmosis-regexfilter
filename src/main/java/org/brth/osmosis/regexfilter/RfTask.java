package org.brth.osmosis.regexfilter;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.core.task.v0_6.SinkSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RfTask implements SinkSource {
    private List<EntityFilter> filters;
    private Sink sink;

    public RfTask(Map<String, String> parameters) {
        filters = new LinkedList<EntityFilter>();
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            filters.add(new EntityFilter(entry));
        }
    }

    @Override
    public void process(EntityContainer entityContainer) {
        Entity entity = entityContainer.getEntity();
        for(EntityFilter filter : filters) {
            if(filter.match(entity)) {
                sink.process(entityContainer);
                return;
            }
        }
    }

    @Override
    public void initialize(Map<String, Object> metaData) {
        sink.initialize(metaData);
    }

    @Override
    public void complete() {
        sink.complete();
    }

    @Override
    public void release() {
        sink.release();
    }

    @Override
    public void setSink(Sink sink) {
        this.sink = sink;
    }
}
