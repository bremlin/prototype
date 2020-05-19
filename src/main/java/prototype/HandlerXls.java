package prototype;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prototype.helpers.PActivityHelper;
import prototype.helpers.PResourceAssignmentHelper;
import prototype.helpers.PWBSHelper;
import prototype.objects.PActivity;
import prototype.objects.PResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class HandlerXls {

    private File mndXls;
    private HashMap<String, PActivity> activityMap = new HashMap<>();
    private PWBSHelper pwbsHelper;
    private PActivityHelper pActivityHelper;
    private PResourceAssignmentHelper pResourceAssignmentHelper;
    private HashMap<String, PResource> mapResources = new HashMap<>();

    public HandlerXls(File mndXls) {
        this.mndXls = mndXls;
    }

    public void loadData() throws IOException {
        FileInputStream fis = new FileInputStream(mndXls);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        pwbsHelper = new PWBSHelper(workbook.getSheet(Main.WBS_DATA));
        pActivityHelper = new PActivityHelper(workbook.getSheet(Main.ACTIVITY_DATA), pwbsHelper);
        pResourceAssignmentHelper = new PResourceAssignmentHelper(workbook.getSheet(Main.RESOURCE_DATA));
//        loadData(workbook.getSheet(Main.RELATION_DATA));
    }

    public PWBSHelper getPwbsHelper() {
        return pwbsHelper;
    }

    public PActivityHelper getpActivityHelper() {
        return pActivityHelper;
    }

    public PResourceAssignmentHelper getpResourceAssignmentHelper() {
        return pResourceAssignmentHelper;
    }

    public void setRelationResource(String mndResource, PResource primaResource) {
        mapResources.put(mndResource, primaResource);
    }

    public PResource getResourcePrima(String resource) {
        return mapResources.get(resource);
    }
}