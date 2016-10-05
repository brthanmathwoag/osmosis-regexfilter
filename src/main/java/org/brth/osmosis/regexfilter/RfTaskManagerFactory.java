package org.brth.osmosis.regexfilter;

import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
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

        String[] modeArgument = getDefaultStringArgument(taskConfig, "").toLowerCase().split("-");
        if (modeArgument.length != 2) {
            throw new Error(
                    "The RegexFilter task's default parameter must consist of an action and an entity type separated by '-'.");
        }

        boolean rejectEntities = parseRejectEntities(modeArgument[0]);
        EntityType entityType = parseEntityType(modeArgument[1]);


        return new SinkSourceManager(
            taskConfig.getId(),
            new RfTask(taskConfig.getConfigArgs(), rejectEntities, entityType),
            taskConfig.getPipeArgs());
    }

    private EntityType parseEntityType(String entityTypeName) {
        if (entityTypeName.endsWith("s")) {
            entityTypeName = entityTypeName.substring(0, entityTypeName.length() - 1);
        }
        switch(entityTypeName) {
            case "node":
                return EntityType.Node;
            case "way":
                return EntityType.Way;
            case "relation":
                return EntityType.Relation;
            default:
                throw new Error(
                        "The RegexFilter entity type must be one of 'node', 'way', or 'relation'. '" + entityTypeName + "' is not a supported entity type.");
        }
    }

    private boolean parseRejectEntities(String actionName) {
        switch(actionName) {
            case "accept":
                return false;
            case "reject":
                return true;
            default:
                throw new Error(
                        "The TagFilter action must be either 'accept' or 'reject'. '" + actionName + "' is not a supported mode.");
        }
    }
}
