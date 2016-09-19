import org.brth.osmosis.regexfilter.RfTask;
import org.junit.Before;
import org.junit.Test;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.CommonEntityData;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.OsmUser;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.testutil.v0_6.SinkEntityInspector;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class RefTaskTests {
    private SinkEntityInspector entityInspector;
    private OsmUser user;

    @Before
    public void setUp() {
        user = new OsmUser(12, "OsmosisTest");
        entityInspector = new SinkEntityInspector();
    }

    @Test
    public final void testPrefixFilter() {
        List<Tag> tags = Arrays.asList(new Tag("alpha:beta", "one"));
        Node node1 = new Node(new CommonEntityData(1104, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node1Container = new NodeContainer(node1);

        tags = Arrays.asList(new Tag("alpha:charlie", "two"));
        Node node2 = new Node(new CommonEntityData(1105, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node2Container = new NodeContainer(node2);

        tags = Arrays.asList(new Tag("charlie:alpha", "three"));
        Node node3 = new Node(new CommonEntityData(1106, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node3Container = new NodeContainer(node3);

        EntityContainer[] containers = { node1Container, node2Container, node3Container };

        HashMap<String, String> map = new HashMap<>();
        map.put("^alpha.*", ".*");
        RfTask task = new RfTask(map);

        task.setSink(entityInspector);
        for(EntityContainer container : containers) {
            task.process(container);
        }
        task.complete();

        List<EntityContainer> expectedResult = Arrays.asList(node1Container, node2Container);
        assertTrue(entityInspector.getProcessedEntities().equals(expectedResult));
        task.release();
    }

    @Test
    public final void testSuffixFilter() {
        List<Tag> tags = Arrays.asList(new Tag("beta:alpha", "one"));
        Node node1 = new Node(new CommonEntityData(1107, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node1Container = new NodeContainer(node1);

        tags = Arrays.asList(new Tag("charlie:delta", "two"));
        Node node2 = new Node(new CommonEntityData(1108, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node2Container = new NodeContainer(node2);

        tags = Arrays.asList(new Tag("gamma:alpha", "three"));
        Node node3 = new Node(new CommonEntityData(1109, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node3Container = new NodeContainer(node3);

        EntityContainer[] containers = { node1Container, node2Container, node3Container };

        HashMap<String, String> map = new HashMap<>();
        map.put(".*alpha$", ".*");
        RfTask task = new RfTask(map);

        task.setSink(entityInspector);
        for(EntityContainer container : containers) {
            task.process(container);
        }
        task.complete();

        List<EntityContainer> expectedResult = Arrays.asList(node1Container, node3Container);
        assertTrue(entityInspector.getProcessedEntities().equals(expectedResult));
        task.release();
    }

    @Test
    public final void testPrefixSuffixFilter() {
        List<Tag> tags = Arrays.asList(new Tag("alpha:beta", "one"));
        Node node1 = new Node(new CommonEntityData(1113, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node1Container = new NodeContainer(node1);

        tags = Arrays.asList(new Tag("alpha:gamma:beta", "two"));
        Node node2 = new Node(new CommonEntityData(1114, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node2Container = new NodeContainer(node2);

        tags = Arrays.asList(new Tag("alpha:beta:gamma", "three"));
        Node node3 = new Node(new CommonEntityData(1115, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node3Container = new NodeContainer(node3);

        tags = Arrays.asList(new Tag("gamma:alpha:beta", "four"));
        Node node4 = new Node(new CommonEntityData(1116, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node4Container = new NodeContainer(node4);

        EntityContainer[] containers = { node1Container, node2Container, node3Container, node4Container };

        HashMap<String, String> map = new HashMap<>();
        map.put("^alpha.*beta$", ".*");
        RfTask task = new RfTask(map);

        task.setSink(entityInspector);
        for(EntityContainer container : containers) {
            task.process(container);
        }
        task.complete();

        List<EntityContainer> expectedResult = Arrays.asList(node1Container, node2Container);
        assertTrue(entityInspector.getProcessedEntities().equals(expectedResult));
        task.release();
    }

    @Test
    public final void testInfixFilter() {
        List<Tag> tags = Arrays.asList(new Tag("beta:alpha", "one"));
        Node node1 = new Node(new CommonEntityData(1110, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node1Container = new NodeContainer(node1);

        tags = Arrays.asList(new Tag("charlie:delta", "two"));
        Node node2 = new Node(new CommonEntityData(1111, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node2Container = new NodeContainer(node2);

        tags = Arrays.asList(new Tag("a:a", "three"));
        Node node3 = new Node(new CommonEntityData(1112, 0, new Date(), user, 0, tags), 1, 2);
        EntityContainer node3Container = new NodeContainer(node3);

        EntityContainer[] containers = { node1Container, node2Container, node3Container };

        HashMap<String, String> map = new HashMap<>();
        map.put(".*a:a.*", ".*");
        RfTask task = new RfTask(map);

        task.setSink(entityInspector);
        for(EntityContainer container : containers) {
            task.process(container);
        }
        task.complete();

        List<EntityContainer> expectedResult = Arrays.asList(node1Container, node3Container);
        assertTrue(entityInspector.getProcessedEntities().equals(expectedResult));
        task.release();
    }
}
