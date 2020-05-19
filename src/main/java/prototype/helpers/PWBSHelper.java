package prototype.helpers;

import javafx.scene.control.TreeItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import prototype.objects.PWBS;

import java.util.ArrayList;
import java.util.HashMap;

public class PWBSHelper {

    private HashMap<Integer, ArrayList<PWBS>> wbsMap = new HashMap<>();
    private HashMap<Integer, PWBS> wbss = new HashMap<>();
    private TreeItem root;

    public PWBSHelper(Sheet sheet) {
        int i = 0;
        for (Row row : sheet) {
            if (i++ < 1) continue;
            PWBS pwbs = new PWBS(row);
            wbss.put(pwbs.getMndId(), pwbs);
            if (wbsMap.containsKey(pwbs.getParentId())) {
                wbsMap.get(pwbs.getParentId()).add(pwbs);
            } else {
                ArrayList<PWBS> tempList = new ArrayList<>();
                tempList.add(pwbs);
                wbsMap.put(pwbs.getParentId(), tempList);
            }
        }
        setStructure();
    }

    private void setStructure() {
        root = new TreeItem("WBS");
        if (wbsMap.containsKey(0)) addChild(root, 0);
    }

    private void addChild(TreeItem item, Integer parentId) {
        for (PWBS pwbs : wbsMap.get(parentId)) {
            TreeItem child = new TreeItem(pwbs);
            item.getChildren().add(child);
            if (wbsMap.containsKey(pwbs.getMndId())) addChild(child, pwbs.getMndId());
        }
    }

    public HashMap<Integer, ArrayList<PWBS>> getWbsMap() {
        return wbsMap;
    }

    public TreeItem getRoot() {
        return root;
    }

    public HashMap<Integer, PWBS> getWbss() {
        return wbss;
    }

//    public String getWbsName(Integer id) {
//
//    }
}