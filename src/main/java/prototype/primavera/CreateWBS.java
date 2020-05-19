package prototype.primavera;

import com.primavera.ServerException;
import com.primavera.integration.client.Session;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Project;
import com.primavera.integration.client.bo.object.WBS;
import com.primavera.integration.network.NetworkException;
import prototype.objects.PWBS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateWBS {

    private Session session;
    private WBS wbsRoot;
    private Project project;

    private HashMap<Integer, PWBS> ppwbsHashMap = new HashMap<>();

    public CreateWBS(Session session, WBS wbs, Project project) {
        this.session = session;
        this.wbsRoot = wbs;
        this.project = project;
    }

//    public void create() throws SQLException, BusinessObjectException, ServerException, NetworkException {
//        Connection connection = SQLConnector.ConnectDb();
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM project");
//        PPProject ppProject = null;
//        while (resultSet.next()) {
//            ppProject = new PPProject(resultSet);
//        }
//        resultSet.close();
//        statement.close();
//        connection.close();
//
//        WBS wbs = new WBS(session);
//        wbs.setParentObjectId(wbsRoot.getObjectId());
//        wbs.setName(ppProject.getName());
//        wbs.setCode(ppProject.getId());
//        wbs.create();
//
//        createWbs(project);
//        createActivity(project);
//    }
//
//    private void createWbs(Project project) throws BusinessObjectException, ServerException, NetworkException, SQLException {
//        if (project != null) {
//
//            int seq = 1;
//
//            HashMap<Integer, ArrayList<PPWBS>> wbsParentMap = new HashMap<>();
//
//            Connection connection = SQLConnector.ConnectDb();
//
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM wbs");
//            PPWBS ppwbs = null;
//            while (resultSet.next()) {
//                ppwbs = new PPWBS(resultSet);
//                ppwbsHashMap.put(ppwbs.getPrototypeId(), ppwbs);
//                if (wbsParentMap.containsKey(ppwbs.getParentId())) {
//                    wbsParentMap.get(ppwbs.getParentId()).add(ppwbs);
//                } else {
//                    ArrayList<PPWBS> tempList = new ArrayList<>();
//                    tempList.add(ppwbs);
//                    wbsParentMap.put(ppwbs.getParentId(), tempList);
//                }
//            }
//            resultSet.close();
//            statement.close();
//            connection.close();
//
//            for (Integer integer : wbsParentMap.keySet()) {
//                for (PPWBS ppwbs1 : wbsParentMap.get(integer)) {
//                    if (integer == 0) {
//                        WBS wbs = new WBS(session);
//                        wbs.setParentObjectId(wbsRoot.getObjectId());
//                        wbs.setName(ppwbs1.getName());
//                        wbs.setCode(ppwbs1.getId());
//                        wbs.setSequenceNumber(seq++);
//                        wbs.create();
//                    } else {
//                        try {
//                            BOIterator<WBS> iterator = project.loadAllWBS(new String[] {
//                                    "ObjectId", "Name"
//                            }, "Name = '" + ppwbsHashMap.get(ppwbs1.getParentId()).getName() + "'", null);
//
//                            while (iterator.hasNext()) {
//                                WBS wbs2 = new WBS(session);
//                                wbs2.setParentObjectId(iterator.next().getObjectId());
//                                wbs2.setName(ppwbs1.getName());
//                                wbs2.setCode(ppwbs1.getId());
//                                wbs2.setSequenceNumber(seq++);
//                                wbs2.create();
//                            }
//                        } catch (ServerException | NetworkException | BusinessObjectException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void createActivity(Project project) throws SQLException {
//
//        Connection connection = SQLConnector.ConnectDb();
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM activity");
//        PPActivity ppActivity = null;
//        while (resultSet.next()) {
//            ppActivity = new PPActivity(resultSet);
//
//            try {
//                BOIterator<WBS> iterator = project.loadAllWBS(new String[] {
//                        "ObjectId", "Name"
//                }, "Name = '" + ppwbsHashMap.get(ppActivity.getParentId()).getName() +  "'", null);
//                int count = 0;
//                while (iterator.hasNext()) {
//                    WBS wbs = iterator.next();
//                    Activity activity = new Activity(session);
//                    activity.setName(ppActivity.getName());
//                    activity.setWBSObjectId(wbs.getObjectId());
//                    activity.create();
//                }
//            } catch (ServerException | NetworkException | BusinessObjectException e) {
//                e.printStackTrace();
//            }
//        }
//        resultSet.close();
//        statement.close();
//        connection.close();
//    }
}
