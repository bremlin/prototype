package prototype.objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PActivity extends PObject {

    private Integer objectId;
    private Integer duration;
    private Integer wbsId;
    private String id;
    private String name;
    private String startDateString;
    private String finishDateString;
    private Date startDate;
    private Date finishDate;
    private String type;
    private ArrayList<String> predecessorList = new ArrayList();
    private ArrayList<String> successor = new ArrayList();
    private ArrayList<PResourceAssignment> raList = new ArrayList();
    private DateFormat format;

    public PActivity(Row row) {
        this.format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        int i = 0;
        this.setId(row.getCell(i));
        this.setName(row.getCell(i++));
        this.setWbsId(row.getCell(i++));
        this.setDuration(row.getCell(i++));

        try {
            this.setStartDate(row.getCell(i++));
            this.setFinishDate(row.getCell(i++));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.setType(row.getCell(i));
    }

    public ArrayList<String> getPredecessorList() {
        return this.predecessorList;
    }

    public ArrayList<String> getSuccessor() {
        return this.successor;
    }

    public ArrayList<PResourceAssignment> getRaList() {
        return this.raList;
    }

    public Integer getObjectId() {
        return this.objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.duration = Integer.valueOf(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.duration = (int)cell.getNumericCellValue();
            }
        } else {
            this.duration = 0;
        }
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

    public String getName() {
        return this.name;
    }

    public void setName(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.name = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    this.name = String.valueOf(cell.getNumericCellValue());
            }
        } else {
            this.name = "";
        }
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Cell cell) throws ParseException {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.startDate = this.format.parse(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.startDate = cell.getDateCellValue();
                    this.setStartDateString(this.format.format(this.startDate));
            }
        }
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Cell cell) throws ParseException {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.finishDate = this.format.parse(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.finishDate = cell.getDateCellValue();
                    this.setFinishDateString(this.format.format(this.finishDate));
            }
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

    public Integer getWbsId() {
        return this.wbsId;
    }

    public void setWbsId(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case STRING:
                    this.wbsId = Integer.valueOf(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    this.wbsId = (int) cell.getNumericCellValue();
            }
        } else {
            this.wbsId = 0;
        }
    }

    public String getStartDateString() {
        return this.startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getFinishDateString() {
        return this.finishDateString;
    }

    public void setFinishDateString(String finishDateString) {
        this.finishDateString = finishDateString;
    }
}