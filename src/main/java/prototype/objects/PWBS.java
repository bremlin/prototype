package prototype.objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class PWBS {

    private Integer objectId;
    private Integer parentId;
    private Integer mndId;

    private String id;
    private String name;

    public PWBS(Row row) {
        int i = 0;
        setMndId(row.getCell(i++));
        setParentId(row.getCell(i++));
        setId(row.getCell(i++));
        setName(row.getCell(i));
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

    public void setParentId(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    this.parentId = Integer.valueOf(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.parentId = (int) cell.getNumericCellValue();
                    break;
            }
        } else {
            this.parentId = 0;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    this.id = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    this.id = String.valueOf(cell.getNumericCellValue());
                    break;
            }
        } else {
            this.id = "0";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    this.name = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    this.name = String.valueOf(cell.getNumericCellValue());
                    break;
            }
        } else {
            this.name = "0";
        }
    }

    public Integer getMndId() {
        return mndId;
    }

    public void setMndId(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    this.mndId = Integer.valueOf(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.mndId = (int) cell.getNumericCellValue();
                    break;
            }
        } else {
            this.mndId = 0;
        }
    }

    @Override
    public String toString() {
        return id + " - /// - " + name;
    }
}