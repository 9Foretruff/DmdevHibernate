package comm.foretruff.interceptor;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;

public class GlobalInterceptor implements Interceptor { //deprecated


    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
