package com.premiumminds.internship.teknonymy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
        return ""; // Se a pessoa não tiver filhos, não há tecnónimo
    } else {
        Person oldestLastGenerationChild = findOldestLastGenerationChild(person);
        return generateTeknonymy(person, oldestLastGenerationChild);
    }
  }

  public static int getMaxGenerations(Person person) {
    if (person.children() == null) {
        return 1; // A pessoa é uma folha na árvore
    }
    int maxGenerations = 0;
    for (Person child : person.children()) {
        maxGenerations = Math.max(maxGenerations, getMaxGenerations(child));
    }
    return maxGenerations + 1;
  }

  private Person findOldestLastGenerationChild(Person person) {
    if (person.children() == null) {
        return person;
    }
    return Arrays.stream(person.children())
                 .map(this::findOldestLastGenerationChild)
                 .min(Comparator.comparing(Person::dateOfBirth))
                 .orElse(person);
  }

  private String generateTeknonymy(Person person, Person lastGenerationChild) {
    String sex = person.sex() == 'M' ? "father" : "mother";
    return sex + " of " + lastGenerationChild.name();
  }
}
