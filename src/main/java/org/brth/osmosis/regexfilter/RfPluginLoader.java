package org.brth.osmosis.regexfilter;

import org.openstreetmap.osmosis.core.plugin.PluginLoader;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;

import java.util.HashMap;
import java.util.Map;

public class RfPluginLoader implements PluginLoader {
    @Override
    public Map<String, TaskManagerFactory> loadTaskFactories() {
        HashMap<String, TaskManagerFactory> map = new HashMap<String, TaskManagerFactory>();
        RfTaskManagerFactory factory = new RfTaskManagerFactory();
        map.put("regex-filter", factory);
        map.put("rf", factory);
        return map;
    }
}
