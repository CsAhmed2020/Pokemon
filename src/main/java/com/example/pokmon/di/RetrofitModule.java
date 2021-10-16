package com.example.pokmon.di;


import com.example.pokmon.network.PokemonApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//Retrofit need (UI<model.PokemonResponse> && ApiService<network.PokemonApiService> && Builder<here>)
//to make builder we need it to be (over whole app & singleton <using one time>),
// for that we will use hilt
//if there a class we have/write it , we can use @inject with its constructor to let us
// fetch its data or edit over it
//if class we doesn't write (Like Retrofit which i can't edit its class )
// we will create a module for it (like this class)
//we create this module to provide retrofit object(builder) to dagger hilt
// when dagger hilt need it
//we have (Activity...ViewMode....Repository....RetrofitApi) ,
//(Activity >>use/need>> ViewMode >>use/need>> Repository >>use/need>> RetrofitApi)
//(Activity <<as dependency to activity<< ViewMode <<as dependency to previous<< Repository
// <<as dependency to previous one << RetrofitApi)
// therefor we use dependency injection here to provide it to the next phase (Repository)


@Module //to know hilt that this is a module
@InstallIn(ApplicationComponent.class) //use it on whole/all app (its Scope)(installIn one of scope annotations)
public class RetrofitModule {

    @Provides
    @Singleton //one for whole app
    public static PokemonApiService providePokemonApiService(){
        return new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) //to inform this builder it will using in background with rx java
                .build().create(PokemonApiService.class);
    }
}
