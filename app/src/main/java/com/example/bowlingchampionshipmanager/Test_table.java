package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*import javax.persistence.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence; */



@Entity(tableName = "test_table")
public class Test_table {


    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name="name")
    private String name;

    public Test_table(int id, String name) {
       // super();
        this.id = id;
        this.name = name;

    }


    public String getName() {
        return name;
    }

    public int getId (){ return id;}


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //delete
  /*  public static void main(String [] args){
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
    } */

}
