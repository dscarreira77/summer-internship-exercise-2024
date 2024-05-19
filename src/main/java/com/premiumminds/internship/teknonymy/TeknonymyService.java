package com.premiumminds.internship.teknonymy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    if (getMaxGenerations(person) == 1) { 
        return ""; // Se a pessoa não tiver filhos, não há tecnônimo
    } else {
        List<Person> lastGenerationChildren = new ArrayList<>();
        //get all last generation children
        getAllLastGeneration(person, lastGenerationChildren,getMaxGenerations(person),0);
        //find the oldest child
        Person oldestChild = findOldestChild(lastGenerationChildren);
        return generateTeknonymy(person, oldestChild);
    }
}

  /**
   * Method to get the maximum number of generations of a person
   * @param person
   * @return int which is the maximum number of generations of a person
   */
  public static int getMaxGenerations(Person person) {
      if (person.children() == null) {
          return 1; // A pessoa é uma folha na árvore
      }
      int maxGenerations = 0;
      //get the maximum number of generations of each child
      for (Person child : person.children()) {
          maxGenerations = Math.max(maxGenerations, getMaxGenerations(child));
      }
      return maxGenerations + 1;
  }


  /**
   * Method to get all the last generation children of a person
   * @param person
   * @param lastGenerationChildren
   * @param maxGeneration
   * @param currentGeneration
   */
  private void getAllLastGeneration(Person person, List<Person> lastGenerationChildren, int maxGeneration, int currentGeneration) {
    //if person is last generation child add to list
    if (currentGeneration == maxGeneration - 1 && (person.children() == null)){
        lastGenerationChildren.add(person);
    } else if (person.children() != null) {
        //if person has children, get all last generation children of each child
        for (Person child : person.children()) {
            getAllLastGeneration(child, lastGenerationChildren, maxGeneration, currentGeneration + 1);
        }
    }
  }


  /**
   * Method to find the oldest child of a list of children
   * @param children
   * @return Person which is the oldest child
   */
  private Person findOldestChild(List<Person> children) {
      //if there are no children return null to prevent null pointer exception
      if (children.isEmpty()) {
          return null;
      }
      Person oldestChild = children.get(0);
      //find the oldest child by comparing the date of birth
      for (Person child : children) {
          if (child.dateOfBirth().isBefore(oldestChild.dateOfBirth())) {
            oldestChild = child;
          }
      }
      return oldestChild;
  }


  /**
   * Method to generate the Teknonymy Name of a person
   * @param person
   * @param lastGenerationChild
   * @return String which is the Teknonymy Name
   */
  private String generateTeknonymy(Person person, Person lastGenerationChild) {
      int generation = getMaxGenerations(person);
      String role = "";
      if (person.sex() == 'M'){
          if (generation == 2){
            role = "father";
          }
          else if (generation == 3){
            role = "grandfather";
          }
          else if (generation == 4){
            role = "great-grandfather";
          }
          else if (generation == 5){
            role = "great-great-grandfather";
          }
          else if (generation > 5){
            role = "ancestor";
          }
          return role + " of " + lastGenerationChild.name();
      }
      else {
          if (generation == 2){
            role = "mother";
          }
          else if (generation == 3){
            role = "grandmother";
          }
          else if (generation == 4){
            role = "great-grandmother";
          }
          else if (generation == 5){
            role = "great-great-grandmother";
          }else if (generation > 5){
            role = "ancestor";
          }
          return role + " of " + lastGenerationChild.name();
      }
    }
}