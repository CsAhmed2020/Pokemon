package com.example.pokmon;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

//FIRST CLASS OPENED WHEN RUN APP , LET IT IMPLEMENT HILT FROM HERE
//Here android system will use this App class instead of using the extended 'Application' class directly
//to let system use App class , add to manifest <Application>... android:name=".App"

@HiltAndroidApp
public class App extends Application {
}
