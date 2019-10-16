package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class EagerLazyDemo {

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

			//get the instructor from DB
			int theId = 1;
			Instructor tempInstructor = session.get(Instructor.class,  theId);
			
			System.out.println("\n\nluv2code: Instructor: " + tempInstructor);
		
			System.out.println("\n\nluv2code: Courses: " + tempInstructor.getCourses());
			
			//commit transaction
			session.getTransaction().commit();
			
			//close the session
			session.close();
			
			System.out.println("\n\nluv2code: The session is now closed\n");
			//since the courses are lazy loaded ... this should fail
			
			//option 1: call getter method while session is open
			
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
