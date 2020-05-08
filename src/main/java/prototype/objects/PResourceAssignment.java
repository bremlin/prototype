package prototype.objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class PResourceAssignment {
    private String id;
    private String type;
    private Double value;
    private Integer priceUnit;
    private boolean primary;

    public PResourceAssignment(Row row) {
        int i = 0;
        this.setId(row.getCell(i));
        this.setType(row.getCell(i++));
        this.setValue(row.getCell(i++));
        this.setPrimary(row.getCell(i++));
        this.setPriceUnit(row.getCell(i));
    }

    public String getId() {
        return this.id;
    }

    public void setId(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.id = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    this.id = String.valueOf(cell.getNumericCellValue());
            }
        } else {
            this.id = "";
        }
    }

    public String getType() {
        return this.type;
    }

    public void setType(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.type = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    this.type = String.valueOf(cell.getNumericCellValue());
            }
        } else {
            this.type = "";
        }
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.value = Double.valueOf(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.value = cell.getNumericCellValue();
            }
        } else {
            this.value = 0.0D;
        }
    }

    public boolean isPrimary() {
        return this.primary;
    }

    public void setPrimary(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.primary = (cell.getStringCellValue().equals("1"));
                    break;
                case NUMERIC:
                    this.primary = (cell.getNumericCellValue() == 1.0D);
            }
        } else {
            this.primary = false;
        }
    }

    public Integer getPriceUnit() {
        return this.priceUnit;
    }

    public void setPriceUnit(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.priceUnit = Integer.valueOf(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.priceUnit = (int)cell.getNumericCellValue();
            }
        } else {
            this.priceUnit = 0;
        }
    }
}