package com.newland.springdataexample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Transactional
	public void savePersons(List<Person> persons) {
		personRepository.save(persons);
	}

	@Transactional
	public void updatePersonEmail(String email, Integer id) {
		personRepository.updatePersonEmail(id, email);
	}
}
