package prototype.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import prototype.objects.PObject;
import prototype.objects.PResourceAssignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PResourceAssignmentHelper {

    private HashMap<String, ArrayList<PResourceAssignment>> raMap = new HashMap<>();
    private HashSet<String> resourceSet = new HashSet<>();
    private TreeItem<String> rootItem = new TreeItem<>();

    public PResourceAssignmentHelper(Sheet sheet) {
        int i = 0;
        for (Row row : sheet) {
            if (i++ < 1) continue;
            PResourceAssignment ra = new PResourceAssignment(row);
            resourceSet.add(ra.getType());
            if (raMap.containsKey(ra.getId())) {
                raMap.get(ra.getId()).add(ra);
            } else {
                ArrayList<PResourceAssignment> tempList = new ArrayList<>();
                tempList.add(ra);
                raMap.put(ra.getId(), tempList);
            }
        }
    }

    public ObservableList<PResourceAssignment> getResources(String id) {
        ObservableList list = FXCollections.observableArrayList();
        list.addAll(raMap.get(id));
        return list;
    }

    public HashSet<String> getResourceSet() {
        return resourceSet;
    }

    public TreeItem<String> getResourceList() {
        for (String s : resourceSet) {
            rootItem.getChildren().add(new TreeItem<>(s));
        }
        return rootItem;
    }
}
