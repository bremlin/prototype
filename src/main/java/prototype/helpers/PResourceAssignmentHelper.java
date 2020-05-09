package prototype.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import prototype.objects.PResourceAssignment;

import java.util.ArrayList;
import java.util.HashMap;

public class PResourceAssignmentHelper {

    private HashMap<String, ArrayList<PResourceAssignment>> raMap = new HashMap<>();

    public PResourceAssignmentHelper(Sheet sheet) {
        int i = 0;
        for (Row row : sheet) {
            if (i++ < 1) continue;
            PResourceAssignment ra = new PResourceAssignment(row);
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
}
