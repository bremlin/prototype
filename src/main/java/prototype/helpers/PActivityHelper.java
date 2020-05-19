package prototype.helpers;

import javafx.scene.control.TreeItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import prototype.objects.PActivity;
import prototype.objects.PWBS;

import java.util.ArrayList;
import java.util.HashMap;

public class PActivityHelper {

    private HashMap<Integer, ArrayList<PActivity>> activityMap = new HashMap<>();
    private PWBSHelper pwbsHelper;
    private ArrayList<PActivity> activityList = new ArrayList<>();

    private TreeItem root;

    public PActivityHelper(XSSFSheet sheet, PWBSHelper pwbsHelper) {
        this.pwbsHelper = pwbsHelper;

        int i = 0;
        for (Row row : sheet) {
            if (i++ < 1) continue;
            PActivity activity = new PActivity(row);
            activityList.add(activity);
            if (activityMap.containsKey(activity.getWbsId())) {
                activityMap.get(activity.getWbsId()).add(activity);
            } else {
                ArrayList<PActivity> tempList = new ArrayList<>();
                tempList.add(activity);
                activityMap.put(activity.getWbsId(), tempList);
            }
        }
        setStructure();
    }

    private void setStructure() {
        this.root = pwbsHelper.getRoot();
        addChild(root);
    }

    private void addChild(TreeItem treeItem) {
        if (treeItem.getChildren().size() > 0) {
            for (Object child : treeItem.getChildren()) {
                TreeItem childItem = (TreeItem) child;
                addChild(childItem);
            }
        }
        if (treeItem.getValue().getClass().equals(PWBS.class)) {
            PWBS pwbs = (PWBS) treeItem.getValue();
            if (activityMap.containsKey(pwbs.getMndId())) {
                for (PActivity activity : activityMap.get(pwbs.getMndId())) {
                    treeItem.getChildren().add(new TreeItem<>(activity));
                }
            }
        }
    }

    public TreeItem getRoot() {
        return root;
    }

    public ArrayList<PActivity> getActivityList() {
        return activityList;
    }
}