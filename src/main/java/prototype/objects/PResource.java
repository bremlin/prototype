package prototype.objects;

import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Resource;

public class PResource extends PObject {

    private Integer objectId;
    private Integer parentObjectId;
    private Integer uomId;

    private String id;
    private String name;
    private String resourceType;

    public PResource(Resource resource) throws BusinessObjectException {
        this.objectId = resource.getObjectId().toInteger();
        this.parentObjectId = resource.getObjectId().toInteger();
//        this.uomId = resource.getUnitOfMeasureObjectId().toInteger();

        this.id = resource.getId();
        this.name = resource.getName();
        this.resourceType = resource.getResourceType().toString();
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(Integer parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
