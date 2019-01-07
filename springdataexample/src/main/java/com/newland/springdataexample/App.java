package com.newland.springdataexample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * Hello world!
 *
 */
public class App {
	private ApplicationContext ctx = null;
	private PersonRepository personRepository = null;
	private PersonService personService;

	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		personRepository = ctx.getBean(PersonRepository.class);
		personService = ctx.getBean(PersonService.class);
	}

	@Test
	public void testCustomRepositoryMethod() {
		personRepository.test2();
	}


	@Test
	public void testJpaRepository() {
		Person person = new Person();
		person.setBirth(new Date());
		person.setEmail("xy@atguigu.com");
		person.setLastName("xyz");
		person.setId(28);

		Person person2 = personRepository.saveAndFlush(person);

		System.out.println(person == person2);
	}

	@Test
	public void testPagingAndSortingRespository() {
		// pageNo 从 0 开始.
		int pageNo = 6 - 1;
		int pageSize = 5;
		// Pageable 接口通常使用的其 PageRequest 实现类. 其中封装了需要分页的信息
		// 排序相关的. Sort 封装了排序的信息
		// Order 是具体针对于某一个属性进行升序还是降序.
		Order order1 = new Order(Direction.DESC, "id");
		Order order2 = new Order(Direction.ASC, "email");
		Sort sort = new Sort(order1, order2);

		PageRequest pageable = new PageRequest(pageNo, pageSize, sort);
		Page<Person> page = personRepository.findAll(pageable);

		System.out.println("总记录数: " + page.getTotalElements());
		System.out.println("当前第几页: " + (page.getNumber() + 1));
		System.out.println("总页数: " + page.getTotalPages());
		System.out.println("当前页面的 List: " + page.getContent());
		System.out.println("当前页面的记录数: " + page.getNumberOfElements());
	}

	@Test
	public void testCrudReposiory() {
		List<Person> persons = new ArrayList<Person>();

		for (int i = 'a'; i <= 'z'; i++) {
			Person person = new Person();
			person.setAddressId(i + 1);
			person.setBirth(new Date());
			person.setEmail((char) i + "" + (char) i + "@atguigu.com");
			person.setLastName((char) i + "" + (char) i);

			persons.add(person);
		}

		personService.savePersons(persons);
	}

	@Test
	public void testModifying() {
		// personRepository.updatePersonEmail(1, "mmmm@atguigu.com");
		personService.updatePersonEmail("mmmm@atguigu.com", 1);
	}

	@Test
	public void testNativeQuery() {
		long count = personRepository.getTotalCount();
		System.out.println(count);
	}

	@Test
	public void testQueryAnnotationLikeParam() {
		// List<Person> persons =
		// personRepository.testQueryAnnotationLikeParam("%A%", "%bb%");
		// System.out.println(persons.size());

		// List<Person> persons =
		// personRepository.testQueryAnnotationLikeParam("A", "bb");
		// System.out.println(persons.size());

		List<Person> persons = personRepository.testQueryAnnotationLikeParam2("bb", "A");
		System.out.println(persons.size());
	}

	@Test
	public void testQueryAnnotationParams2() {
		List<Person> persons = personRepository.testQueryAnnotationParams2("aa@atguigu.com", "AA");
		System.out.println(persons);
	}

	@Test
	public void testQueryAnnotationParams1() {
		List<Person> persons = personRepository.testQueryAnnotationParams1("AA", "aa@atguigu.com");
		System.out.println(persons);
	}

	@Test
	public void testQueryAnnotation() {
		Person person = personRepository.getMaxIdPerson();
		System.out.println(person);
	}

	@Test
	public void testKeyWords2() {
		List<Person> persons = personRepository.getByAddress_IdGreaterThan(1);
		System.out.println(persons);
	}

	@Test
	public void testKeyWords() {
		List<Person> persons = personRepository.getByLastNameStartingWithAndIdLessThan("X", 10);
		System.out.println(persons);

		persons = personRepository.getByLastNameEndingWithAndIdLessThan("X", 10);
		System.out.println(persons);

		persons = personRepository.getByEmailInAndBirthLessThan(
				Arrays.asList("AA@atguigu.com", "FF@atguigu.com", "SS@atguigu.com"), new Date());
		System.out.println(persons.size());
	}

	@Test
	public void testHelloWorldSpringData()
			throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException {
		System.out.println(personRepository.getClass().getName());

		Person person = personRepository.getByLastName("AA");
		System.out.println(person);
	}

	@Test
	public void testJpa() {

	}

	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}
}
