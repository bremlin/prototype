package prototype;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prototype.helpers.PWBSHelper;
import prototype.objects.PActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class HandlerXls {

    private File mndXls;
    private HashMap<String, PActivity> activityMap = new HashMap<>();
    private PWBSHelper pwbsHelper = new PWBSHelper();

    public HandlerXls(File mndXls) {
        this.mndXls = mndXls;
    }

    public void loadData() throws IOException {
        System.out.println("lets go");

        FileInputStream fis = new FileInputStream(mndXls);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        loadWbsData(workbook.getSheet(Main.WBS_DATA));
        loadActivityData(workbook.getSheet(Main.ACTIVITY_DATA));
        loadResourceData(workbook.getSheet(Main.RESOURCE_DATA));
        loadRelationData(workbook.getSheet(Main.RELATION_DATA));
    }

    private void loadWbsData(XSSFSheet sheet) {
        for (Row row : sheet) {
            int i = 0;

        }
    }

    private void loadActivityData(XSSFSheet sheet) {
        int i = 0;
        for (Row row : sheet) {
            i++;
            if (i > 1) {
                int j = 0;
                PActivity activity = new PActivity();
                activity.setId(row.getCell(j++).getStringCellValue());
                activity.setName(row.getCell(j++).getStringCellValue());
                Cell cell;
            }
        }
    }

    private void loadResourceData(XSSFSheet sheet) {

    }

    private void loadRelationData(XSSFSheet sheet) {

    }

}
