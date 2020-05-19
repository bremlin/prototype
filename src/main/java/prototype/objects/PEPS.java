package prototype.objects;

import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.EPS;

public class PEPS extends PObject{

    private Integer objectId;
    private Integer parentId;
    private Integer seq;

    private String id;
    private String name;

    public PEPS(EPS eps) {
        try {
            this.objectId = eps.getObjectId().toInteger();
            if (eps.getParentObjectId() != null) this.parentId = eps.getParentObjectId().toInteger();
            this.seq = eps.getSequenceNumber();
            this.id = eps.getId();
            this.name = eps.getName();
        } catch (BusinessObjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return id + '\'' +
                name + '\'';
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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
