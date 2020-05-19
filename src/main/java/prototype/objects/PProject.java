package prototype.objects;

import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Project;

public class PProject extends PObject {

    private Integer prototypeId;
    private Integer objectId;
    private Integer parentId;

    private String id;
    private String name;

    public PProject(Project project) {
        try {
            this.objectId = project.getObjectId().toInteger();
            this.parentId = project.getParentEPSObjectId().toInteger();
            this.id = project.getId();
            this.name = project.getName();
        } catch (BusinessObjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return id + '\'' +
                name + '\'';
    }

    public Integer getPrototypeId() {
        return prototypeId;
    }

    public void setPrototypeId(Integer prototypeId) {
        this.prototypeId = prototypeId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
}
