package com.example.pokmon.network;

import com.example.pokmon.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

//Retrofit need (UI<model.PokemonResponse> && ApiService<here> && Builder<di.RetrofitModule>)
public interface PokemonApiService {

    // UI ..... ApiInterface(here) ......Server
    //to wrapping(collect) all data(Response) in background thread not in main thread
    // we use rx observable
    //to let retrofit full this func with response we use @GET("part after base url")

    @GET("pokemon")
    Observable<PokemonResponse> getPokemon();

    //i can't build the instance here as i can't use dagger with retrofit as
    // it doesn't written by me so i create RetrofitModule
}
