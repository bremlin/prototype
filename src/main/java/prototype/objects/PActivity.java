package prototype.objects;

import java.util.ArrayList;
import java.util.Date;

public class PActivity {

    private Integer objectId;
    private Integer duration;

    private String id;
    private String name;

    private Date startDate;
    private Date finishDate;

    private String type;

    private ArrayList<String> predecessorList = new ArrayList<>();
    private ArrayList<String> Successor = new ArrayList<>();

    private ArrayList<PResourceAssignment> raList = new ArrayList<>();

    public ArrayList<String> getPredecessorList() {
        return predecessorList;
    }

    public ArrayList<String> getSuccessor() {
        return Successor;
    }

    public ArrayList<PResourceAssignment> getRaList() {
        return raList;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
