package com.example.usersapp.data.local_db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.usersapp.data.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    private static volatile AppDatabase instance;

    public static AppDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized (AppDatabase.class) {
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }

        return instance;
    }
}
