package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    System.out.println(result);
    System.out.println(expected);
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test 
  public void TwoGenerationsTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', new Person[]{ new Person("Mary",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) }, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Mary";
    assertEquals(result, expected);
  }

  @Test
  public void getOldestChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)), new Person("Mary",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    Person[] personList = person.children();
    Person result = new TeknonymyService().getOldestChild(personList);
    Person expected = personList[0];
    assertEquals(result, expected);
  }

  @Test
  public void getLastGenerationChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)), new Person("Mary",'F', null, LocalDateTime.of(1044, 1, 1, 0, 0)) },
        LocalDateTime.of(1020, 1, 1, 0, 0));
        String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Mary";
    assertEquals(result, expected);
  }

  @Test
  public void GivenTree(){
    Person F = new Person("F", 'M', null, LocalDateTime.of(2018,1,1,0,0));
    Person G = new Person("G", 'M', null, LocalDateTime.of(2021,1,1,0,0));
    Person H = new Person("H", 'M', null, LocalDateTime.of(2022,1,1,0,0));

    Person E = new Person("E",'M',null, LocalDateTime.of(2019,1,1,0,0));

    Person D = new Person("D",'F',new Person[]{F,G,H}, LocalDateTime.of(2003,1,1,0,0));
    Person C = new Person("C",'M',null, LocalDateTime.of(2002,1,1,0,0));
    Person B = new Person("B",'M',new Person[]{E}, LocalDateTime.of(2000,1,1,0,0));

    Person A = new Person("A",'M',new Person[]{B,C,D}, LocalDateTime.of(1990,1,1,0,0));

    String result = new TeknonymyService().getTeknonymy(A);
    String expected = "father of F";

    assertEquals(result, expected);
  }
}