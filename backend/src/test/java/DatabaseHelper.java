import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hh.nab.hibernate.NabSessionFactoryBean;

import javax.transaction.Transactional;
import java.util.Objects;

public class DatabaseHelper {

    @Autowired
    private final NabSessionFactoryBean sessionFactoryBean;

    public DatabaseHelper(NabSessionFactoryBean sessionFactoryBean) { this.sessionFactoryBean = sessionFactoryBean; }

    @Transactional
    public void clean(String... entityNames) {
        for (String entityName : entityNames) {
            getCurrentSession().createQuery("delete from " + entityName).executeUpdate();
        }
    }

    private Session getCurrentSession() {
        return Objects.requireNonNull(sessionFactoryBean.getObject()).getCurrentSession();
    }

}
