package com.luv2code.hibernate.demo;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class CreateDemo {

	public static void main(String [] args){
		
//		//Adding standard log4j.properties
//		BasicConfigurator.configure();
		
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			//create the objects
			/*
			Instructor tempInstructor = 
					new Instructor("Peter", "Parker", "IamSpiderman@marvel.com");
			
			InstructorDetail tempInstructorDetail =
					new InstructorDetail(
							"https://www.youtube.com/watch?v=BARjPuUN36Y",
							"Wallclimbing");
			*/
			
			Instructor tempInstructor = 
					new Instructor("Tony", "Stark", "IamIronman@marvel.com");
			
			InstructorDetail tempInstructorDetail =
					new InstructorDetail(
							"https://www.youtube.com/ironmantriathlon",
							"Braging");
			
			//associate the objects together
			tempInstructor.setInstructorDetail(tempInstructorDetail);
			
			//start a transaction
			session.beginTransaction();
			
			//save the instructor
			//Because of cascade.all, will also save tempInstructorDetail
			System.out.println("Saving instructor: " + tempInstructor);
			session.save(tempInstructor);
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
	}
}
