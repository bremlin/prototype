package prototype.helpers;

import com.primavera.ServerException;
import com.primavera.integration.client.Session;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Resource;
import com.primavera.integration.network.NetworkException;
import prototype.objects.PResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ResourceHelper {

    private Session session;
    private HashMap<Integer, PResource> resourceMap = new HashMap<>();
    private HashMap<Integer, ArrayList<PResource>> relationResourceMap = new HashMap<>();

    public ResourceHelper(Session session) {
        this.session = session;
    }

    public void run() throws BusinessObjectException, ServerException, NetworkException {
        BOIterator<Resource> iterator = session.getGlobalObjectManager().loadResources(new String[] {
                "ObjectId", "Name", "Id", "ParentObjectId", "ResourceType"
        }, null, null);
        PResource resource;
        while (iterator.hasNext()) {
            Resource resourcePrim = iterator.next();
            resource = new PResource(resourcePrim);
            resourceMap.put(resource.getObjectId(), resource);
        }
    }

    private void setStructure() {

    }

    public Collection<PResource> getResource() {
        return resourceMap.values();
    }
}