package comm.foretruff.listener;

import comm.foretruff.entity.Revision;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionListener;

import java.lang.annotation.Annotation;

public class ForetruffRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        //SecurityContext.getUser().getId()
        ((Revision)revisionEntity).setUsername("foretruff");
    }

}
