package br.com.app.markae

import android.app.Application
import br.com.app.markae.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@AppApplication)
			modules(databaseModule)
		}
	}
}