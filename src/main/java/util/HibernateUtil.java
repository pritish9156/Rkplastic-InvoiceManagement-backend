package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Bill;
import model.BillItem;
import model.Customer;

public class HibernateUtil {

	static SessionFactory sessionFactory = null;

	static {

		try {

			System.out.println("Hibernate Starting...");

			Configuration config = new Configuration();

			config.configure();

			config.addAnnotatedClass(Customer.class);

			config.addAnnotatedClass(Bill.class);

			config.addAnnotatedClass(BillItem.class);

			sessionFactory = config.buildSessionFactory();

			System.out.println("Hibernate Started Successfully");

		} catch (Exception e) {

			System.out.println("Hibernate Failed");

			e.printStackTrace();
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
