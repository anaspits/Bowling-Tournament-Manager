package com.example.bowlingchampionshipmanager;

import javax.persistence.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@Entity
@Table(name="test_table")
public class Test_table {

    @Id
    private int id;

    @Column(name="name")
    private String name;

    public Test_table(int id, String name) {
        super();
        this.id = id;
        this.name = name;

    }

    public Test_table() {
        super();
    }

    public String get_name() {
        return name;
    }

    public int get_id (){ return id;}


    public void set_id(int id) {
        this.id = id;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public static void main(String [] args){
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("Participant_details");
        EntityManager em=emf.createEntityManager();
        em.getTransaction().begin();
        Test_table s1 = new Test_table(0, "test");
        em.persist(s1);
        //Test_table s=em.find(Test_table.class,1);
        //s.set_name("yes");
        em.getTransaction().commit();
        emf.close();
        em.close();
    }

}
