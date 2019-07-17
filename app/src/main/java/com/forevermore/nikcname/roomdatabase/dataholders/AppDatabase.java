package com.forevermore.nikcname.roomdatabase.dataholders;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContactData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao getContactDao();
}
