package br.com.app.markae.di

import androidx.room.Room
import br.com.app.markae.data.local.database.NotesDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule  = module {
	single {
		Room.databaseBuilder(
				androidApplication(),
				NotesDatabase::class.java,
				"DATABASE"
		)
			.fallbackToDestructiveMigration(false)
			.build()
	}

	single { get<NotesDatabase>().notesDao() }
}