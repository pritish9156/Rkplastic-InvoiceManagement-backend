package DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Bill;
import util.HibernateUtil;

public class BillDAO {

    // Save
    public boolean save(Bill bill) {

        Transaction tx = null;

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            tx = session.beginTransaction();

            session.persist(bill);

            tx.commit();

            return true;

        } catch(Exception e) {

            if(tx != null)
                tx.rollback();

            e.printStackTrace();
            return false;
        }
    }

    // Find By Id
    public Bill findById(int id) {

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.get(Bill.class, id);
        }
    }

    // Get All Bills
    public List<Bill> findAll() {

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session
                    .createQuery("select distinct b "
                    		+ "from Bill b "
                    		+ "left join fetch b.customer "
                    		+ "left join fetch b.items", Bill.class)
                    .list();
        }
    }
    
    
    public Bill updateBill(
            Bill bill) {

        Transaction tx = null;

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            tx =
                    session.beginTransaction();

            Bill merged =
                    (Bill) session.merge(
                            bill);

            tx.commit();

            return merged;

        } catch(Exception e) {

            if(tx != null)
                tx.rollback();

            throw e;
        }
    }

    public boolean existsByBillNo(
            Integer billNo) {

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            Long count =
                    session.createQuery(
                           "select count(*) from Bill where billNo=:billNo",
                            Long.class)
                            .setParameter(
                                    "billNo",
                                    billNo)
                            .uniqueResult();

            return count > 0;
        }
    }
    
    // Update
    public Bill update(
            Bill bill) {

        Transaction tx = null;

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            tx =
                    session.beginTransaction();

            Bill merged =
                    (Bill)session.merge(
                            bill);

            tx.commit();

            return merged;
        }
        catch(Exception e) {

            if(tx != null)
                tx.rollback();

            throw e;
        }
    }

    // Delete
    public boolean delete(int id) {

        Transaction tx = null;

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Bill bill = session.get(Bill.class, id);

            if(bill == null)
                return false;

            tx = session.beginTransaction();

            session.remove(bill);

            tx.commit();

            return true;

        } catch(Exception e) {

            if(tx != null)
                tx.rollback();

            e.printStackTrace();

            return false;
        }
    }
    
    public Bill findBillDetails(int billId) {

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            String hql =
                "select distinct b from Bill b left join fetch b.customer left join fetch b.items where b.id = :id";

            return session.createQuery(hql, Bill.class)
                    .setParameter("id", billId)
                    .uniqueResult();
        }
    }
    
    public Bill findByBillNo(int billNo) {

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "select distinct b "
                    + "from Bill b "
                    + "left join fetch b.customer "
                    + "left join fetch b.items "
                    + "where b.billNo = :billNo",
                    Bill.class)
                    .setParameter("billNo", billNo)
                    .uniqueResult();
        }
    }
    
    public List<Bill> findByCustomer(int customerId) {

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "from Bill where customer.id = :id",
                    Bill.class)
                    .setParameter("id", customerId)
                    .list();
        }
    }
    
    public Integer getNextBillNo() {

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            Integer maxBillNo =
                    session.createQuery(
                            "select max(b.billNo) from Bill b",
                            Integer.class)
                            .uniqueResult();

            return maxBillNo == null
                    ? 1
                    : maxBillNo + 1;
        }
    }
    
    
    public List<Bill> findBetweenDates(
            LocalDate from,
            LocalDate to) {

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            return session.createQuery(
                    "from Bill where billDate between :from and :to order by billDate desc",
                    Bill.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .list();
        }
    }
    
    public List<Bill> findByCustomerName(
            String keyword) {

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            return session.createQuery(
                    "select b from Bill b join b.customer c where lower(c.name) like lower(:name)",
                    Bill.class)
                    .setParameter(
                            "name",
                            "%" + keyword + "%")
                    .list();
        }
    }
    
    public Bill saveAndReturn(
            Bill bill) {

        Transaction tx = null;

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            tx =
                    session.beginTransaction();

            session.persist(bill);

            tx.commit();

            return bill;

        } catch(Exception e) {

            if(tx != null)
                tx.rollback();

            throw e;
        }
    }
    
    public Long getInvoiceCount() {

        try(Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session
                    .createQuery(
                            "select count(*) from Bill",
                            Long.class)
                    .uniqueResult();
        }
    }
    
    public BigDecimal getTotalRevenue() {

        try(Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session
                    .createQuery(
                            "select sum(grandTotal) from Bill",
                            BigDecimal.class)
                    .uniqueResult();
        }
    }
}