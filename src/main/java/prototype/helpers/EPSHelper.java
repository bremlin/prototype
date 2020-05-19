package prototype.helpers;

import com.primavera.ServerException;
import com.primavera.integration.client.Session;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.EPS;
import com.primavera.integration.network.NetworkException;
import javafx.scene.control.TreeItem;
import prototype.objects.PEPS;

import java.util.ArrayList;
import java.util.HashMap;

public class EPSHelper {

    private Session session;
    private HashMap<Integer, PEPS> epsMap = new HashMap<>();
    private HashMap<Integer, ArrayList<PEPS>> parentMap = new HashMap<>();
    private PEPS root;
    private TreeItem<PEPS> rootItem;

    public EPSHelper(Session session) {
        this.session = session;
    }

    public void load() throws BusinessObjectException, ServerException, NetworkException {
        BOIterator<EPS> iterator = session.getGlobalObjectManager().loadEPS(new String[] {
                "ObjectId", "Name", "Id", "ParentObjectId", "SequenceNumber"
        }, null, null);
        PEPS eps = null;
        while (iterator.hasNext()) {
            eps = new PEPS(iterator.next());
            if (eps.getParentId() == null || eps.getParentId() == 0) {
                root = eps;
                rootItem = new TreeItem<>(eps);
            } else {
                if (parentMap.containsKey(eps.getParentId())) {
                    parentMap.get(eps.getParentId()).add(eps);
                } else {
                    ArrayList<PEPS> tempList = new ArrayList<>();
                    tempList.add(eps);
                    parentMap.put(eps.getParentId(), tempList);
                }
            }
            epsMap.put(eps.getObjectId(), eps);

        }
        if (parentMap.containsKey(0)) {
            setStructure(new TreeItem(eps), 0);
        }
    }

    private void setStructure(TreeItem item, Integer id) {
        for (PEPS peps : parentMap.get(id)) {
            TreeItem childItem = new TreeItem(peps);
            item.getChildren().add(childItem);
            if (parentMap.containsKey(peps.getObjectId())) {
                setStructure(childItem, peps.getObjectId());
            }
        }
    }

    public HashMap<Integer, PEPS> getEpsMap() {
        return epsMap;
    }

    public PEPS getRoot() {
        return root;
    }

    public TreeItem<PEPS> getRootItem() {
        return rootItem;
    }
}
