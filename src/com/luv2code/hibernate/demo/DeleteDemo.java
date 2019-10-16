package com.luv2code.hibernate.demo;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class DeleteDemo {

	public static void main(String [] args){
		
		//Adding standard log4j.properties
		BasicConfigurator.configure();
		
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {

			//start a transaction
			session.beginTransaction();

			// get the instructor by their primary key - id
			int theId = 2;
			Instructor tempInstructor =
					session.get(Instructor.class, theId);
			
			System.out.println("Found Instructor: " + tempInstructor);
			//delete instructor
			if (tempInstructor != null) {
				System.out.println("Deleting: " + tempInstructor);
				
				//Will also delete the detailIntstructor for the desired tempInstructor because of cascadeType.all
				session.delete(tempInstructor);
			}
			
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
	}
}
