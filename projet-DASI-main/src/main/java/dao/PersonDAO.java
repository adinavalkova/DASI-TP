package dao;

import metier.modele.Person;

public class PersonDAO {

    /**
     * Persists a new Person entity in the database.
     *
     * @param person the Person object to be persisted
     */
    public void create(Person person) {
        JpaUtil.obtenirContextePersistance().persist(person);
    }
    
    
    /**
     * Retrieves a {@link Person} entity from the database using its unique identifier.
     *
     * @param id the unique identifier of the {@link Person} to retrieve
     * @return the {@link Person} entity with the specified id, or {@code null} if not found
     */
    public Person findByID(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Person.class, id);
    }
    

    /**
     * Retrieves a {@link Person} entity from the database using the specified email address.
     *
     * @param mail the email address of the person to find
     * @return the {@link Person} entity matching the given email address
     * @throws javax.persistence.NoResultException if no person with the specified email address is found
     * @throws javax.persistence.NonUniqueResultException if more than one person with the specified email address is found
     */
    public Person findByMail(String mail){
        return JpaUtil.obtenirContextePersistance().createQuery("select p from Person p where p.emailAddress=:mail", Person.class).
            setParameter("mail", mail)
            .getSingleResult();
    }
}