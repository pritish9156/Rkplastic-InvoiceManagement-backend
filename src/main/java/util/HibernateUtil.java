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

            System.out.println("DB_URL = " + System.getenv("DB_URL"));
            System.out.println("DB_USERNAME = " + System.getenv("DB_USERNAME"));
            System.out.println("DB_PASSWORD = " + System.getenv("DB_PASSWORD"));
            
            sessionFactory =
                    config.buildSessionFactory();

            System.out.println("Hibernate Started Successfully");

        }
        catch (Exception e) {

        	 System.out.println("Hibernate Failed");
        	 e.printStackTrace();
        	 throw new RuntimeException(e);

        }

    }

    public static SessionFactory getSessionFactory() {

        return sessionFactory;

    }

}