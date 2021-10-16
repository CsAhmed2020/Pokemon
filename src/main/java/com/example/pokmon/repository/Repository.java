package com.example.pokmon.repository;

import androidx.lifecycle.LiveData;

import com.example.pokmon.db.PokemonDao;
import com.example.pokmon.model.Pokemon;
import com.example.pokmon.model.PokemonResponse;
import com.example.pokmon.network.PokemonApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

//a viewModel doesn't need to know how or where we fetch the data
//there we create repo to be a link between viewModel and data(retrofitApi) &&
//ViewModel and Dao
public class Repository {
    private PokemonApiService pokemonApiService;
    private PokemonDao pokemonDao;


    @Inject //inform dagger hilt when you need any object from this repo go get it directly
    public Repository(PokemonApiService pokemonApiService, PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao = pokemonDao;
    }

    public Observable<PokemonResponse> getPokemon() {
        return pokemonApiService.getPokemon();
    }

    public void insertPokemon(Pokemon pokemon) {
        pokemonDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        pokemonDao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getFavPokemons() {
        return pokemonDao.getPokemon();
    }
}
