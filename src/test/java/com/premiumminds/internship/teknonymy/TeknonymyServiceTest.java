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
    String expected = "grandfather of Mary";
    assertEquals(result, expected);
  }

  @Test
  public void ThreeGenerationsTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', new Person[]{ new Person("Mary",'F', new Person[]{ new Person("Jane",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) }, LocalDateTime.of(1046, 1, 1, 0, 0)) }, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandfather of Jane";
    assertEquals(result, expected);
  }

  @Test
  public void TwoGenaritionsEach2ChildsTest(){
    Person F = new Person("F", 'M', null, LocalDateTime.of(2024,1,1,0,0));
    Person G = new Person("G", 'M', null, LocalDateTime.of(2023,1,1,0,0));
    Person H = new Person("H", 'M', null, LocalDateTime.of(2022,1,1,0,0));

    Person E = new Person("E",'M', new Person[]{F,G}, LocalDateTime.of(2019,1,1,0,0));
    Person S = new Person("S",'M',new Person[]{H}, LocalDateTime.of(2019,1,1,0,0));
    Person D = new Person("D",'F',new Person[]{S,E}, LocalDateTime.of(2003,1,1,0,0));

    String result = new TeknonymyService().getTeknonymy(D);
    String expected = "grandmother of H";
    assertEquals(result, expected);
  }

  @Test
  public void GivenTreeTest1(){
    Person F = new Person("F", 'M', null, LocalDateTime.of(2018,1,1,0,0));
    Person G = new Person("G", 'M', null, LocalDateTime.of(2021,1,1,0,0));
    Person H = new Person("H", 'M', null, LocalDateTime.of(2022,1,1,0,0));

    Person E = new Person("E",'M',null, LocalDateTime.of(2019,1,1,0,0));

    Person D = new Person("D",'F',new Person[]{F,G,H}, LocalDateTime.of(2003,1,1,0,0));
    Person C = new Person("C",'M',null, LocalDateTime.of(2002,1,1,0,0));
    Person B = new Person("B",'M',new Person[]{E}, LocalDateTime.of(2000,1,1,0,0));

    Person A = new Person("A",'M',new Person[]{B,C,D}, LocalDateTime.of(1990,1,1,0,0));

    String result = new TeknonymyService().getTeknonymy(A);
    String expected = "grandfather of F";
    assertEquals(result, expected);
  }

@Test
public void GivenTreeTest2(){
  Person F = new Person("F", 'M', null, LocalDateTime.of(2018,1,1,0,0));
  Person G = new Person("G", 'M', null, LocalDateTime.of(2021,1,1,0,0));
  Person H = new Person("H", 'M', null, LocalDateTime.of(2022,1,1,0,0));

  Person E = new Person("E",'M',null, LocalDateTime.of(2019,1,1,0,0));

  Person D = new Person("D",'F',new Person[]{F,G,H}, LocalDateTime.of(2003,1,1,0,0));
  Person C = new Person("C",'M',null, LocalDateTime.of(2002,1,1,0,0));
  Person B = new Person("B",'M',new Person[]{E}, LocalDateTime.of(2000,1,1,0,0));

  Person A = new Person("A",'M',new Person[]{B,C,D}, LocalDateTime.of(1990,1,1,0,0));

  String result = new TeknonymyService().getTeknonymy(B);
  String expected = "father of E";
  assertEquals(result, expected);
}

@Test
public void GivenTreeTest3(){
  Person F = new Person("F", 'M', null, LocalDateTime.of(2018,1,1,0,0));
  Person G = new Person("G", 'M', null, LocalDateTime.of(2021,1,1,0,0));
  Person H = new Person("H", 'M', null, LocalDateTime.of(2022,1,1,0,0));

  Person E = new Person("E",'M',null, LocalDateTime.of(2019,1,1,0,0));

  Person D = new Person("D",'F',new Person[]{F,G,H}, LocalDateTime.of(2003,1,1,0,0));
  Person C = new Person("C",'M',null, LocalDateTime.of(2002,1,1,0,0));
  Person B = new Person("B",'M',new Person[]{E}, LocalDateTime.of(2000,1,1,0,0));

  Person A = new Person("A",'M',new Person[]{B,C,D}, LocalDateTime.of(1990,1,1,0,0));

  String result = new TeknonymyService().getTeknonymy(D);
  String expected = "mother of F";
  assertEquals(result, expected);
}

@Test
public void GivenTreeTest4(){
    Person F = new Person("F", 'M', null, LocalDateTime.of(2018,1,1,0,0));
    Person G = new Person("G", 'M', null, LocalDateTime.of(2021,1,1,0,0));
    Person H = new Person("H", 'M', null, LocalDateTime.of(2022,1,1,0,0));

    Person E = new Person("E",'M',null, LocalDateTime.of(2019,1,1,0,0));

    Person D = new Person("D",'F',new Person[]{F,G,H}, LocalDateTime.of(2003,1,1,0,0));
    Person C = new Person("C",'M',null, LocalDateTime.of(2002,1,1,0,0));
    Person B = new Person("B",'M',new Person[]{E}, LocalDateTime.of(2000,1,1,0,0));

    Person A = new Person("A",'M',new Person[]{B,C,D}, LocalDateTime.of(1990,1,1,0,0));

    String result = new TeknonymyService().getTeknonymy(C);
    String expected = "";
    assertEquals(result, expected);
}


@Test
public void givenTreeTestWithMultipleGenerations() {
        Person J = new Person("J", 'M', null, LocalDateTime.of(2025, 1, 1, 0, 0));
        Person K = new Person("K", 'F', null, LocalDateTime.of(2026, 1, 1, 0, 0));
        
        Person F = new Person("F", 'M', null, LocalDateTime.of(2018, 1, 1, 0, 0));
        Person G = new Person("G", 'M', null, LocalDateTime.of(2021, 1, 1, 0, 0));
        Person H = new Person("H", 'M', null, LocalDateTime.of(2022, 1, 1, 0, 0));
        Person I = new Person("I", 'F', new Person[]{J, K}, LocalDateTime.of(2015, 1, 1, 0, 0));
        
        Person E = new Person("E", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 0));
        Person D = new Person("D", 'F', new Person[]{F, G, H, I}, LocalDateTime.of(2003, 1, 1, 0, 0));
        Person C = new Person("C", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0));
        Person B = new Person("B", 'M', new Person[]{E}, LocalDateTime.of(2000, 1, 1, 0, 0));

        Person A = new Person("A", 'M', new Person[]{B, C, D}, LocalDateTime.of(1990, 1, 1, 0, 0));
        
        // Serviço
        TeknonymyService service = new TeknonymyService();
        
        // Verify for A
        String resultA = service.getTeknonymy(A);
        String expectedA = "great-grandfather of J";
        assertEquals(expectedA, resultA);
        
        // Verify for D
        String resultD = service.getTeknonymy(D);
        String expectedD = "grandmother of J";
        assertEquals(expectedD, resultD);
        
        // Verify for C
        String resultC = service.getTeknonymy(C);
        String expectedC = "";
        assertEquals(expectedC, resultC);
        
        // Verifify for B
        String resultB = service.getTeknonymy(B);
        String expectedB = "father of E";
        assertEquals(expectedB, resultB);
    }
}