package by.it;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import by.it.entity.Department;
import by.it.entity.Employee;
import by.it.util.HibernateUtil;

/**
 * Class EhCacheTest
 *
 * Created by yslabko on 08/30/2017.
 */
public class EhCacheTest {
    @Before
    public void init() {
        Department developer = new Department("Developer");
        Department hr = new Department("HR");
        Department qa = new Department("QA");
        developer.getEmployees().add(new Employee(null, "Yuli", "", new Date(), null, developer, null));
        developer.getEmployees().add(new Employee(null, "Max", "", new Date(), null, developer, null));
        developer.getEmployees().add(new Employee(null, "Paul", "", new Date(), null, developer, null));
        qa.getEmployees().add(new Employee(null, "Gleb", "", new Date(), null, qa, null));
        qa.getEmployees().add(new Employee(null, "Li", "", new Date(), null, qa, null));
        hr.getEmployees().add(new Employee(null, "Alex", "", new Date(), null, hr, null));
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(developer);
        em.persist(qa);
        em.persist(hr);
        em.getTransaction().commit();
        em.clear();
        em.close();
    }

    @Test
    public void cacheTest() {
        Session em = HibernateUtil.getSession();
        Employee developer = em.get(Employee.class, 1L);

        developer = HibernateUtil.getSession().get(Employee.class, 1L);
        em.close();
    }

    @Test
    public void queryCacheTest2() {
        Session em = HibernateUtil.getSession();
        final org.hibernate.query.Query query = em.createQuery("select e from Employee e " +
                "where e.firstname like :name")
                .setParameter("name", "yuli%");
        query.setCacheable(true);

        List<Employee> yulij = query.getResultList();
    }


    @Test
    public void queryCacheTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        Employee yulij = em.createQuery("select e from Employee e " +
                "where e.firstname like :name", Employee.class)
                .setParameter( "name", "yuli%")
                .setHint( "org.hibernate.cacheable", "true")
                .getSingleResult();
//        System.out.println(yulij);
        em.clear();
        yulij = em.createQuery("select e from Employee e " +
                "where e.firstname like :name", Employee.class)
                .setParameter( "name", "yuli%")
                .setHint( "org.hibernate.cacheable", "true")
                .getSingleResult();
//        System.out.println(yulij);
        em.close();
    }

    @Test
    public void transactionTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        Department economist = new Department("Economist");
        try {
            em.getTransaction().begin();
            em.persist(economist);
            // Other business logic stuff
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @AfterClass
    public static void cleanUp() {
        HibernateUtil.closeEMFactory();
    }
}
