package com.example.pokmon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pokmon.model.Pokemon;

@Database(entities = Pokemon.class,version = 1,exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {
    public abstract PokemonDao pokemonDao();

    //i can't build a singleton pattern to create builder to create only one instance from DB
    // with @inject to its constructor here
    //because here in spite of i write this class and Dao but Room which generate its code
    //there for i can't use @inject here (Like Retrofit)
    //i will create a module to create a builder (like retrofit module)

    //in brief i can't generate constructor of build in class and use dagger @inject with it
}
