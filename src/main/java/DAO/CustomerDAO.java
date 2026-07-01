package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Bill;
import model.Customer;
import util.HibernateUtil;

public class CustomerDAO {

    public Customer save(Customer customer) {

        Transaction tx = null;

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            tx = session.beginTransaction();

            session.persist(customer);

            tx.commit();

            return customer;
        }
        catch(Exception e) {

            if(tx != null)
                tx.rollback();

            throw e;
        }
    }

    public Customer findById(Integer id) {

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            return session.get(
                    Customer.class,
                    id);
        }
    }

    public List<Customer> search(
            String keyword) {

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            return session.createQuery(
                    "from Customer where lower(name) like lower(:keyword)",
                    Customer.class)
                    .setParameter(
                            "keyword",
                            "%" + keyword + "%")
                    .list();
        }
    }
    
    
    public Customer findByGstin(
            String gstin) {

        try(Session session =
                    HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

            return session.createQuery(
                    "from Customer where gstin=:gstin",
                    Customer.class)
                    .setParameter(
                            "gstin",
                            gstin)
                    .uniqueResult();
        }
    }
    
    public List<Customer> findAll() {

        try(Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session
                    .createQuery(
                            "from Customer",
                            Customer.class)
                    .list();
        }
    }
    
 // Delete
    public boolean delete(int id) {

        Transaction tx = null;

        try(Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Customer customer = session.get(Customer.class, id);

            if(customer == null)
                return false;

            tx = session.beginTransaction();

            session.remove(customer);

            tx.commit();

            return true;

        } catch(Exception e) {

            if(tx != null)
                tx.rollback();

            e.printStackTrace();

            return false;
        }
    }
}