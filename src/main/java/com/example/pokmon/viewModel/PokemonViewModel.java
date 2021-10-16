package com.example.pokmon.viewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.pokmon.model.Pokemon;
import com.example.pokmon.model.PokemonResponse;
import com.example.pokmon.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;



public class PokemonViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<>();
    private LiveData<List<Pokemon>> favList=null;

    @ViewModelInject //to let dag hilt pass repo to view model (to inject viewModel)
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    //used to observe on returned data in recyclerView
    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    //to get Pokemons from retrofit and full the pokemonList in above function
    @SuppressLint("CheckResult")
    public void getPokemon(){
        repository.getPokemon()
                .subscribeOn(Schedulers.io()) //to implement it in the background thread
                // not main thread,therefor we use Observable in Repo & model
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    //.map(which take , which returned),
                    // we use this func to remap the img url ,as it's not valid ,
                    // it need to edit ,
                    // we edit it by take only the number from img url in api
                    // and add it to the link in string bellow +.png
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for(Pokemon pokemon : list){
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/"); //it will split url according to / and take after  the last / data bellow
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/"+pokemonIndex[pokemonIndex.length-1] +".png");
                        }
                        return list;
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //after finish it return to ui thread,
                // there was error here when call AndroidSchedulers.mainThread()
                // we fix it by convert all imports in rx java from io.reactivex to io.reactivex.rxjava3
        .subscribe(result -> pokemonList.setValue(result),
                error -> Log.d("ViewModel",error.getMessage()));
    }

    public LiveData<List<Pokemon>> getFavList(){
        return favList;
    }

    public void insertPokemon(Pokemon pokemon){
        repository.insertPokemon(pokemon);
    }

    public void  deletePokemon (String pokemonName){
        repository.deletePokemon(pokemonName);
    }

    public void getFavPokemon(){
        favList=repository.getFavPokemons();
    }
}
