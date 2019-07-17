package com.forevermore.nikcname.roomdatabase.dataholders;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insertAll(ContactData... contactData);

    @Delete
    void delete(ContactData contactData);

    @Query("SELECT * FROM ContactData")
    List<ContactData> getAllContactData();

    @Update
    void updateContact(ContactData contactData);

}
