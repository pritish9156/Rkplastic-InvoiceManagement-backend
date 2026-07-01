package DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import dto.MonthlySalesDTO;
import model.Bill;
import util.HibernateUtil;

public class ReportDAO {

    public Long getTotalInvoices() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        Long total =
                session.createQuery(
                        "select count(b.id) from Bill b",
                        Long.class)
                .uniqueResult();

        session.close();

        return total == null ? 0L : total;
    }

    public BigDecimal getTodaySales() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        LocalDate today =
                LocalDate.now();

        BigDecimal total =
                session.createQuery(

                        "select sum(b.grandTotal) " +
                        "from Bill b " +
                        "where b.billDate=:today",

                        BigDecimal.class)

                .setParameter(
                        "today",
                        today)

                .uniqueResult();

        session.close();

        return total == null
                ? BigDecimal.ZERO
                : total;
    }

    public BigDecimal getMonthlySales() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        LocalDate firstDay =
                LocalDate.now()
                .withDayOfMonth(1);

        LocalDate lastDay =
                firstDay.plusMonths(1)
                .minusDays(1);

        BigDecimal total =
                session.createQuery(

                        "select sum(b.grandTotal) " +
                        "from Bill b " +
                        "where b.billDate between :start and :end",

                        BigDecimal.class)

                .setParameter(
                        "start",
                        firstDay)

                .setParameter(
                        "end",
                        lastDay)

                .uniqueResult();

        session.close();

        return total == null
                ? BigDecimal.ZERO
                : total;
    }

    public BigDecimal getTotalGST() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        BigDecimal total =
                session.createQuery(

                        "select " +
                        "sum(coalesce(b.cgst,0)+coalesce(b.sgst,0)+coalesce(b.igst,0)) " +
                        "from Bill b",

                        BigDecimal.class)

                .uniqueResult();

        session.close();

        return total == null
                ? BigDecimal.ZERO
                : total;
    }

    public BigDecimal getCGST() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        BigDecimal total =
                session.createQuery(

                        "select sum(b.cgst) from Bill b",

                        BigDecimal.class)

                .uniqueResult();

        session.close();

        return total == null
                ? BigDecimal.ZERO
                : total;
    }

    public BigDecimal getSGST() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        BigDecimal total =
                session.createQuery(

                        "select sum(b.sgst) from Bill b",

                        BigDecimal.class)

                .uniqueResult();

        session.close();

        return total == null
                ? BigDecimal.ZERO
                : total;
    }

    public BigDecimal getIGST() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        BigDecimal total =
                session.createQuery(

                        "select sum(b.igst) from Bill b",

                        BigDecimal.class)

                .uniqueResult();

        session.close();

        return total == null
                ? BigDecimal.ZERO
                : total;
    }

    public List<Bill> getInvoicesBetweenDates(
            LocalDate start,
            LocalDate end) {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        Query<Bill> query =
                session.createQuery(

                        "from Bill b " +
                        "left join fetch b.customer " +
                        "left join fetch b.items " +
                        "where b.billDate between :start and :end " +
                        "order by b.billDate",

                        Bill.class);

        query.setParameter(
                "start",
                start);

        query.setParameter(
                "end",
                end);

        List<Bill> bills =
                query.list();

        session.close();

        return bills;
    }

    public List<Bill> getAllInvoices() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        List<Bill> bills =
                session.createQuery(

                        "select distinct b " +
                        "from Bill b " +
                        "left join fetch b.customer " +
                        "left join fetch b.items " +
                        "order by b.billDate desc",

                        Bill.class)

                .list();

        session.close();

        return bills;
    }
    
    
    public List<MonthlySalesDTO> getMonthlySalesReport() {

        Session session =
                HibernateUtil
                .getSessionFactory()
                .openSession();

        List<Object[]> rows =
                session.createQuery(

                        """
                        select
                        month(b.billDate),
                        sum(b.grandTotal)
                        from Bill b
                        group by month(b.billDate)
                        order by month(b.billDate)
                        """,

                        Object[].class)

                .list();

        List<MonthlySalesDTO> result =
                new ArrayList<>();

        String[] months = {

                "",

                "Jan",

                "Feb",

                "Mar",

                "Apr",

                "May",

                "Jun",

                "Jul",

                "Aug",

                "Sep",

                "Oct",

                "Nov",

                "Dec"

        };

        for(Object[] row : rows) {

            Integer month =
                    (Integer) row[0];

            BigDecimal amount =
                    (BigDecimal) row[1];

            result.add(

                    new MonthlySalesDTO(

                            months[month],

                            amount

                    )

            );

        }

        session.close();

        return result;

    }

}