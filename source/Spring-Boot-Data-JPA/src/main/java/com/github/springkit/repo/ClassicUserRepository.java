package com.github.springkit.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.springkit.domain.User;

import org.springframework.stereotype.Repository;

@Repository
public class ClassicUserRepository {

	 @PersistenceContext EntityManager em;

	 public List<User> findByFullName(String fullName) {
		 return getEntityManger()
				 .createNamedQuery("User.classicQuery", User.class)
				 .setParameter("fullName", fullName)
				 .getResultList();
	 }
	 
	 
	 private EntityManager getEntityManger() {
		 return em;
	 }
	
}
