package prototype;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prototype.helpers.PActivityHelper;
import prototype.helpers.PWBSHelper;
import prototype.objects.PActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class HandlerXls {

    private File mndXls;
    private HashMap<String, PActivity> activityMap = new HashMap<>();
    private PWBSHelper pwbsHelper;
    private PActivityHelper pActivityHelper;

    public HandlerXls(File mndXls) {
        this.mndXls = mndXls;
    }

    public void loadData() throws IOException {
        FileInputStream fis = new FileInputStream(mndXls);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        loadWbsData(workbook.getSheet(Main.WBS_DATA));
        loadActivityData(workbook.getSheet(Main.ACTIVITY_DATA));
        loadResourceData(workbook.getSheet(Main.RESOURCE_DATA));
        loadRelationData(workbook.getSheet(Main.RELATION_DATA));
    }

    private void loadWbsData(XSSFSheet sheet) {
        pwbsHelper = new PWBSHelper(sheet);
    }

    private void loadActivityData(XSSFSheet sheet) {
        pActivityHelper = new PActivityHelper(sheet);
    }

    private void loadResourceData(XSSFSheet sheet) {

    }

    private void loadRelationData(XSSFSheet sheet) {

    }

    public PWBSHelper getPwbsHelper() {
        return pwbsHelper;
    }
}