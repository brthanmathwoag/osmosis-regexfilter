package org.brth.osmosis.regexfilter;

import org.openstreetmap.osmosis.core.pipeline.common.TaskConfiguration;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManager;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.pipeline.v0_6.SinkSourceManager;

public class RfTaskManagerFactory extends TaskManagerFactory {
    @Override
    protected TaskManager createTaskManagerImpl(TaskConfiguration taskConfig) {
        // Iterate over keyword arguments and fetch them through the appropriate TaskManagerFactory utility method
        // to avoid 'Argument was not recognized' exceptions from Osmosis
        for (String key : taskConfig.getConfigArgs().keySet()) {
            getStringArgument(taskConfig, key);
        }

        return new SinkSourceManager(
            taskConfig.getId(),
            new RfTask(taskConfig.getConfigArgs()),
            taskConfig.getPipeArgs());
    }
}
