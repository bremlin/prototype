package prototype.utils;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import prototype.objects.PResourceAssignment;

public class TableUtils {

    public TableColumn<PResourceAssignment, String> addColumnId() {
        TableColumn<PResourceAssignment, String> column = new TableColumn("ID");
        column.setCellValueFactory(new PropertyValueFactory("id"));
        column.setPrefWidth(column.getPrefWidth() * 3.0D);
        return column;
    }

    public TableColumn<PResourceAssignment, String> addColumnName() {
        TableColumn<PResourceAssignment, String> column = new TableColumn("Наименование");
        column.setCellValueFactory(new PropertyValueFactory("type"));
        column.setPrefWidth(column.getPrefWidth() * 3.0D);
        return column;
    }

    public TableColumn<PResourceAssignment, String> addColumnValue() {
        TableColumn<PResourceAssignment, String> column = new TableColumn("Объём");
        column.setCellValueFactory(new PropertyValueFactory("value"));
        column.setPrefWidth(column.getPrefWidth() * 3.0D);
        return column;
    }

    public TableColumn<PResourceAssignment, Boolean> addColumnPvApply() {
        TableColumn<PResourceAssignment, Boolean> column = new TableColumn("Управляющий ресурс");
        column.setCellValueFactory((param) -> {
            PResourceAssignment pResourceAssignment = (PResourceAssignment)param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(pResourceAssignment.isPrimary());
            return booleanProp;
        });
        this.setCheckBoxFactory(column);
        column.setPrefWidth(column.getPrefWidth() * 3.0D);
        return column;
    }

    private void setCheckBoxFactory(TableColumn<PResourceAssignment, Boolean> column) {
        column.setCellFactory((param) -> {
            CheckBoxTableCell<PResourceAssignment, Boolean> cell = new CheckBoxTableCell();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }
}
