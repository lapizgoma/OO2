package test;

import org.hibernate.Session;

import dao.HibernateUtil;

public class TestHibernate {
	public static void main(String[] args) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    session.close();
	    System.out.println("OK");
	}
}
