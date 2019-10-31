package by.it;

import by.it.entity.Cat;
import by.it.entity.CatLockAll;
import by.it.entity.CatLockDirty;
import by.it.entity.CatLockVersion;
import by.it.util.HibernateUtil;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.HashMap;

public class LockTest {
    @Before
    public void init() {
        Cat cat = new Cat(null, "Cat", "Tim");
        CatLockAll all = new CatLockAll(null, "AllCat", "Tim");
        CatLockDirty dirty = new CatLockDirty(null, "Dirty", "Tim");
        CatLockVersion version = new CatLockVersion(null, "Version", "Tim", null);
//        EntityManager em = HibernateUtil.getEntityManager("by.it.test");
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(cat);
        em.persist(all);
        em.persist(dirty);
        em.persist(version);
        em.getTransaction().commit();
        em.clear();
        em.close();
    }

    @Test
    public void noLockModeTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Cat cat = em.find(Cat.class, 1L);
        cat.setName("New");
        em.getTransaction().commit();
        em.close();
    }


    @Test
    public void optimisticLockModeVersionTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockVersion cat = em.find(CatLockVersion.class, 1L);
        cat.setName("New");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticLockModeAllTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockAll cat = em.find(CatLockAll.class, 1L);
        cat.setName("New");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticLockModeDirtyTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockDirty cat = em.find(CatLockDirty.class, 1L);
        cat.setName("New");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticLockModeTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockVersion cat = em.find(CatLockVersion.class, 1L, LockModeType.OPTIMISTIC);
        cat.setName("New");
        em.getTransaction().commit();
        em.close();
    }


    @Test(expected = javax.persistence.RollbackException.class)
    public void optimisticLockVersionModeExceptionTest() throws InterruptedException {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockVersion cat = em.find(CatLockVersion.class, 1L);
        cat.setName("New");
        new Thread(() -> {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            entityManager.getTransaction().begin();
            CatLockVersion updatedCat = entityManager.find(CatLockVersion.class, 1L);
            updatedCat.setName("Updated Cat");
            entityManager.getTransaction().commit();
        }).start();
        Thread.sleep(200);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticIncrementLockModeTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockVersion cat = em.find(CatLockVersion.class, 1L,
                LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void pessimisticLockModeExceptionTest() throws InterruptedException {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Cat cat = em.find(Cat.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        cat.setName("New");
        new Thread(() -> {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            entityManager.getTransaction().begin();
            Cat updatedCat = entityManager.find(Cat.class, 1L, LockModeType.PESSIMISTIC_WRITE,
                    new HashMap<String, Object>() {{
                        put("javax.persistence.lock.timeout", 0);
                    }});
            updatedCat.setName("Updated Cat");
            entityManager.getTransaction().commit();
        }).start();
        Thread.sleep(500);
        em.getTransaction().commit();
        em.clear();
        System.out.println( "result test" + em.find(Cat.class, 1L));
        em.close();
    }

    @Test
    public void optimisticLockModeExceptionTest() throws InterruptedException {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockVersion cat = em.find(CatLockVersion.class, 1L);
        cat.setName("New");
        new Thread(() -> {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            entityManager.getTransaction().begin();
            CatLockVersion updatedCat = entityManager.find(CatLockVersion.class, 1L);
            updatedCat.setName("Updated Cat");
            entityManager.getTransaction().commit();
        }).start();
        Thread.sleep(500);
        em.getTransaction().commit();
        em.clear();

        System.out.println( "result test" + em.find(Cat.class, 1L));
        em.close();
    }

    @Test
    public void optimisticLockDirtyModeExceptionTest() throws InterruptedException {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        CatLockDirty cat = em.find(CatLockDirty.class, 1L);
        cat.setName("New");
        new Thread(() -> {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            entityManager.getTransaction().begin();
            CatLockDirty updatedCat = entityManager.find(CatLockDirty.class, 1L);
            updatedCat.setName("Updated Cat");
            entityManager.getTransaction().commit();
        }).start();
        Thread.sleep(500);
        em.getTransaction().commit();
        em.clear();

        System.out.println( "result test" + em.find(Cat.class, 1L));
        em.close();
    }

}
