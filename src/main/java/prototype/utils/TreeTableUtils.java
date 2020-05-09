package prototype.utils;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import prototype.objects.PObject;

public class TreeTableUtils {
    public TreeTableColumn<PObject, String> addColumnId() {
        TreeTableColumn<PObject, String> idColumn = new TreeTableColumn("ID");
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory("id"));
        idColumn.setPrefWidth(idColumn.getPrefWidth() * 2.0D);
        return idColumn;
    }

    public TreeTableColumn<PObject, String> addColumnName() {
        TreeTableColumn<PObject, String> idColumn = new TreeTableColumn("Наименование");
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        idColumn.setPrefWidth(idColumn.getPrefWidth() * 6.0D);
        return idColumn;
    }

    public TreeTableColumn<PObject, String> addColumnDuration() {
        TreeTableColumn<PObject, String> idColumn = new TreeTableColumn("Длительность");
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory("duration"));
        idColumn.setPrefWidth(idColumn.getPrefWidth() * 1.0D);
        return idColumn;
    }

    public TreeTableColumn<PObject, String> addColumnStart() {
        TreeTableColumn<PObject, String> idColumn = new TreeTableColumn("Начало");
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory("startDateString"));
        idColumn.setPrefWidth(idColumn.getPrefWidth() * 1.0D);
        return idColumn;
    }

    public TreeTableColumn<PObject, String> addColumnFinish() {
        TreeTableColumn<PObject, String> idColumn = new TreeTableColumn("Окончание");
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory("finishDateString"));
        idColumn.setPrefWidth(idColumn.getPrefWidth() * 1.0D);
        return idColumn;
    }

    public TreeTableColumn<PObject, String> addColumnType() {
        TreeTableColumn<PObject, String> idColumn = new TreeTableColumn("Тип");
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory("type"));
        idColumn.setPrefWidth(idColumn.getPrefWidth() * 2.0D);
        return idColumn;
    }
}
