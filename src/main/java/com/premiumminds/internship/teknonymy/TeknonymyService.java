package com.premiumminds.internship.teknonymy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

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
        int maxGeneration = getMaxGenerations(person);
        Person oldestLastGenerationChild = findOldestLastGenerationChild(person, maxGeneration, 1);
        return generateTeknonymy(person, oldestLastGenerationChild);
    }
  }

  public static int getMaxGenerations(Person person) {
    if (person.children() == null || person.children().length == 0) {
        return 1; // A pessoa é uma folha na árvore
    }
    int maxGenerations = 0;
    for (Person child : person.children()) {
        maxGenerations = Math.max(maxGenerations, getMaxGenerations(child));
    }
    return maxGenerations + 1;
  }

  private Person findOldestLastGenerationChild(Person person, int maxGeneration, int currentGeneration) {
    if (person.children() == null || person.children().length == 0) {
        return person;
    }

    Person oldestChild = null;
    for (Person child : person.children()) {
      if (currentGeneration == maxGeneration - 1) {
        if (oldestChild == null || child.dateOfBirth().isBefore(oldestChild.dateOfBirth())) {
          oldestChild = child;
        }
      } else {
        Person candidate = findOldestLastGenerationChild(child, maxGeneration, currentGeneration + 1);
        if (oldestChild == null || candidate.dateOfBirth().isBefore(oldestChild.dateOfBirth())) {
          oldestChild = candidate;
        }
      }
    }

    return oldestChild;
  }

  private String generateTeknonymy(Person person, Person lastGenerationChild) {
    String role = person.sex() == 'M' ? "father" : "mother";
    return role + " of " + lastGenerationChild.name();
  }
}