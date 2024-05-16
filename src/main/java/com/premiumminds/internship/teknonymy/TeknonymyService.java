package com.premiumminds.internship.teknonymy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    
    String sex;

    if(person.children() == null){ 
      return "";
    }else{

      Person child = getLastGenerationChild(person);

      if(person.sex() == 'M'){
        sex = "father of ";
      }else{
        sex = "mother of ";
      }
      return sex + child.name();
    }
  }

  public Person getLastGenerationChild(Person person){
    if (person.children() == null){
      return person;
    }else{
      for (Person child : person.children()){
        if (child.children() == null){
          if (getOldestChild(person.children()) == child){
            return child;
        }
        }else{
          return getLastGenerationChild(child);
          }
        }
      }
    return person;
  }
  
  public Person getOldestChild(Person[] personList){
    LocalDateTime Date = personList[0].dateOfBirth();
    for (Person person: personList){
      if (person.dateOfBirth().isBefore(Date)){
        Date = person.dateOfBirth();
      }
    }
    for (Person person: personList){
      if (person.dateOfBirth() == Date){
        System.out.println(person.name());
        System.out.println(person.dateOfBirth());
        System.out.println(Date);
        return person;
      }
    }

    return null;
  }

}
