package de.jethroo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import de.jethroo.Person.Sex;

public class JavaLambda {

	public static List<Person> initializePersons() {
		ArrayList<Person> list = new ArrayList<>();

		Person p1 = new Person();
		p1.setBirthday(LocalDate.of(1979, 11, 12));
		p1.setName("Carsten Wirth");
		p1.setGender(Sex.MALE);
		p1.setEmailAddress("cwirth79@web.de");

		Person p2 = new Person();
		p2.setBirthday(LocalDate.of(1986, 6, 22));
		p2.setName("Madame Incocnito");
		p2.setGender(Sex.FEMALE);

		list.add(p1);
		list.add(p2);
		return list;
	}

	public static void main(String[] args) {
		List<Person> roster = initializePersons();
		// Lambda for filtering the list of persons
		Predicate<Person> tester = p -> p.getGender() == Person.Sex.MALE
				&& p.getAge() >= 18 && p.getAge() <= 37;
		// function which is applied to all found (not filtered) persons
		Function<Person, String> function = p -> p.toString();
		// consumes the result of the function
		Consumer<String> consumer = s -> System.out.println(s);

		processItems(roster, tester, function, consumer);

		// aggregate Operations via collection API
		roster.stream().filter(tester).map(function).forEach(consumer);
	}

	public static <X, Y> void processItems(List<X> roster, Predicate<X> tester,
			Function<X, Y> function, Consumer<Y> consumer) {
		for (X p : roster) {
			if (tester.test(p)) {
				consumer.accept(function.apply(p));
			}
		}
	}
}

class Person {
	public enum Sex {
		MALE, FEMALE
	}

	String name;
	LocalDate birthday;
	Sex gender;
	String emailAddress;

	public int getAge() {
		if (birthday != null) {
			return (int) Math.abs(ChronoUnit.YEARS.between(birthday,
					LocalDate.now()));
		} else {
			return -1;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String toString() {
		return String.format("Name: %s, Age: %s, Gender: %s, Email: %s", name,
				getAge(), gender, emailAddress);
	}
}
