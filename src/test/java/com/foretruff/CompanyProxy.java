package com.foretruff;

import comm.foretruff.entity.Company;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.proxy.ProxyConfiguration;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor;

public class CompanyProxy extends Company
        implements HibernateProxy , ProxyConfiguration {
    private ByteBuddyInterceptor buddyInterceptor;

    @Override
    public Object writeReplace() {
        return null;
    }

    @Override
    public LazyInitializer getHibernateLazyInitializer() {
        return buddyInterceptor;
    }

    @Override
    public void $$_hibernate_set_interceptor(Interceptor interceptor) {

    }
}
