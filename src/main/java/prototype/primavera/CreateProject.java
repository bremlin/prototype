package prototype.primavera;

import com.primavera.ServerException;
import com.primavera.common.value.ObjectId;
import com.primavera.integration.client.Session;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Project;
import com.primavera.integration.client.bo.object.WBS;
import com.primavera.integration.network.NetworkException;
import prototype.helpers.PActivityHelper;
import prototype.helpers.PWBSHelper;
import prototype.objects.PActivity;
import prototype.objects.PEPS;
import prototype.objects.PProject;
import prototype.objects.PWBS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateProject {

    private Session session;
    private PEPS eps;

    private HashMap<Integer, WBS> wbsMap = new HashMap<>();
    private HashMap<Integer, PWBS> pwbsHashMap = new HashMap<>();

    private PActivityHelper activityHelper;
    private PWBSHelper wbsHelper;
    private PProject pProject;

    public CreateProject(Session session, PEPS eps, PActivityHelper activityHelper, PWBSHelper wbsHelper, PProject pProject) {
        this.session = session;
        this.eps = eps;
        this.activityHelper = activityHelper;
        this.wbsHelper = wbsHelper;
        this.pProject = pProject;
    }

    public void create() throws BusinessObjectException, ServerException, NetworkException {
        Project project = new Project(session);
        project.setId(pProject.getId());
        project.setName(pProject.getName());
        project.setParentEPSObjectId(new ObjectId(eps.getObjectId()));
        project.create();

        createWbs(loadProjectData(project));
        createActivity(loadProjectData(project));
    }

    private Project loadProjectData(Project project) {
        try {
            BOIterator<Project> iterator = dLogin.session.getGlobalObjectManager().loadProjects(new String[] {
                    "ObjectId", "Name", "Id", "ParentEPSObjectId", "WBSObjectId"
            }, "Id = '" + project.getId() + "'", null);
            int count = iterator.getAll().length;
            int i = 0;
            System.out.println("count Projects: " + count);

            while (iterator.hasNext()) {
                return iterator.next();
            }
        } catch (ServerException | NetworkException | BusinessObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createWbs(Project project) throws BusinessObjectException, ServerException, NetworkException {
        if (project != null) {

            int seq = 1;


            for (Integer integer : wbsHelper.getWbsMap().keySet()) {
                for (PWBS ppwbs1 : wbsHelper.getWbsMap().get(integer)) {
                    if (integer == 0) {
                        WBS wbs = new WBS(session);
                        wbs.setParentObjectId(project.getWBSObjectId());
                        wbs.setName(ppwbs1.getName());
                        wbs.setCode(ppwbs1.getId());
                        wbs.setSequenceNumber(seq++);
                        wbs.create();
                    } else {
                        try {
                            BOIterator<WBS> iterator = project.loadAllWBS(new String[] {
                                    "ObjectId", "Name"
                            }, "Name = '" + pwbsHashMap.get(ppwbs1.getParentId()).getName() + "'", null);

                            while (iterator.hasNext()) {
                                WBS wbs2 = new WBS(session);
                                wbs2.setParentObjectId(iterator.next().getObjectId());
                                wbs2.setName(ppwbs1.getName());
                                wbs2.setCode(ppwbs1.getId());
                                wbs2.setSequenceNumber(seq++);
                                wbs2.create();
                            }
                        } catch (ServerException | NetworkException | BusinessObjectException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void createActivity(Project project) {

        for (PActivity pActivity : activityHelper.getActivityList()) {
            try {
                BOIterator<WBS> iterator = project.loadAllWBS(new String[] {
                        "ObjectId", "Name"
                }, "Name = '" + pwbsHashMap.get(pActivity.getWbsId()).getName() +  "'", null);
                int count = 0;
                while (iterator.hasNext()) {
                    WBS wbs = iterator.next();
                    Activity activity = new Activity(session);
                    activity.setName(pActivity.getName());
                    activity.setWBSObjectId(wbs.getObjectId());
                    activity.create();
                }
            } catch (ServerException | NetworkException | BusinessObjectException e) {
                e.printStackTrace();
            }
        }
    }
}
