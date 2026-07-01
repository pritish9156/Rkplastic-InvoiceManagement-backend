package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Bill;
import model.BillItem;
import model.Customer;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {

        try {

            System.out.println("Hibernate Starting...");

            Configuration config = new Configuration();

            config.configure();

            config.setProperty(
                    "hibernate.connection.url",
                    System.getenv("DB_URL"));

            config.setProperty(
                    "hibernate.connection.username",
                    System.getenv("DB_USERNAME"));

            config.setProperty(
                    "hibernate.connection.password",
                    System.getenv("DB_PASSWORD"));

            config.addAnnotatedClass(Customer.class);
            config.addAnnotatedClass(Bill.class);
            config.addAnnotatedClass(BillItem.class);

            sessionFactory =
                    config.buildSessionFactory();

            System.out.println("Hibernate Started Successfully");

        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static SessionFactory getSessionFactory() {

        return sessionFactory;

    }

}