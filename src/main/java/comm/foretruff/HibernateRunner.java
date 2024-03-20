package comm.foretruff;

import comm.foretruff.entity.User;
import comm.foretruff.entity.UserChat;
import comm.foretruff.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
//            session.enableFetchProfile("withCompany");

            var userGraph = session.createEntityGraph(User.class);
            userGraph.addAttributeNodes("company","userChats");
            var userChats = userGraph.addSubgraph("userChats", UserChat.class);
            userChats.addAttributeNodes("chat");

            Map<String, Object> properties = Map.of(
                    GraphSemantic.LOAD.getJakartaHintName(),
                    userGraph
//                    session.getEntityGraph("WithCompanyAndChat")
            );

            var user = session.find(User.class, 1L, properties);
            System.out.println(user.getCompany().getName());
            System.out.println(user.getUserChats().size());

            var users = session.createQuery(
                            "from User", User.class)
//                    .setHint(GraphSemantic.LOAD.getJakartaHintName(), session.getEntityGraph("WithCompanyAndChat"))
                    .setHint(GraphSemantic.LOAD.getJakartaHintName(), userGraph)
//                    .setMaxResults(5)
                    .list();
            users.forEach(it -> System.out.println(it.getUserChats().size()));
            users.forEach(it -> System.out.println(it.getCompany().getName()));
            session.getTransaction().commit();
        }
    }
}
