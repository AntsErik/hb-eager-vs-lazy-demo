package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class FetchJoinDemo {

	public static void main(String [] args){
		
//		//Adding standard log4j.properties
//		BasicConfigurator.configure();
		
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)								
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {

			//start a transaction
			session.beginTransaction();
			
			//option 2: Hibernate query with HQL

			//get the instructor from DB
			int theId = 1;

			Query<Instructor> query =
					session.createQuery("select i from Instructor i "
										+ "JOIN FETCH i.courses "
										+ "WHERE i.id=:theInstructorId", 
										Instructor.class);
			
			//set parameter
			query.setParameter("theInstructorId", theId);
			
			//execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();
			
			System.out.println("\n\nluv2code: Instructor: " + tempInstructor);
		
			
			//commit transaction
			session.getTransaction().commit();
			
			//close the session
			session.close();
			
			System.out.println("\n\nluv2code: The session is now closed\n");
			//since the courses are lazy loaded ... this should fail
			
			//get courses for instruction
			System.out.println("\n\nluv2code: Courses: " + tempInstructor.getCourses());
			
			System.out.println("\n\nluv2code: Done!");
		}
		finally {
			
			//add cleanup mode
			session.close();
			
			factory.close();
		}
	}
}
