package by.it.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtil {
    private static EntityManagerFactory emFactory=null;

    public static EntityManager getEntityManager() {
        return getEntityManager("by.it");
    }

    public static EntityManager getEntityManager(String unit) {
        if (emFactory == null) {
            emFactory  = Persistence.createEntityManagerFactory(unit);
        }
        return emFactory.createEntityManager();
    }

    public static Session getSession(){
        return getEntityManager().unwrap(Session.class);
    }

    public static void closeEMFactory() {
        emFactory.close();
    }
}
