package olyarisu.github.com.myapplication

import android.app.Application
import olyarisu.github.com.myapplication.di.apiModule
import olyarisu.github.com.myapplication.di.moduleDb
import olyarisu.github.com.myapplication.di.repositoryModule
import olyarisu.github.com.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@MainApplication)
            // declare modules
            modules(listOf(viewModelModule, repositoryModule, moduleDb, apiModule))
        }
    }
}