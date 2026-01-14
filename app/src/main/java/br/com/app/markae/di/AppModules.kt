package br.com.app.markae.di

import androidx.room.Room
import br.com.app.markae.data.local.database.NotesDatabase
import br.com.app.markae.data.repository.NoteRepositoryImpl
import br.com.app.markae.domain.repository.NoteRepository
import br.com.app.markae.domain.usecase.DeleteAllNotesUseCase
import br.com.app.markae.domain.usecase.DeleteNoteUseCase
import br.com.app.markae.domain.usecase.GetAllNotesUseCase
import br.com.app.markae.domain.usecase.GetNoteUseCase
import br.com.app.markae.domain.usecase.InsertNoteUseCase
import br.com.app.markae.domain.usecase.TogglePinUseCase
import br.com.app.markae.domain.usecase.UpdateNoteUseCase
import br.com.app.markae.ui.viewmodel.ListNotesViewModel
import br.com.app.markae.ui.viewmodel.NoteViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
	single {
		Room.databaseBuilder(
			androidApplication(),
			NotesDatabase::class.java,
			"DATABASE"
		)
			.fallbackToDestructiveMigration(true)
			.build()
	}

	single { get<NotesDatabase>().notesDao() }
}

val repositoryModule = module {
	single<NoteRepository> { NoteRepositoryImpl(get()) }
}

val useCaseModule = module {
	single { InsertNoteUseCase(get()) }
	single { DeleteNoteUseCase(get()) }
	single { DeleteAllNotesUseCase(get()) }
	single { UpdateNoteUseCase(get()) }
	single { TogglePinUseCase(get()) }
	single { GetAllNotesUseCase(get()) }
	single { GetNoteUseCase(get()) }
}

val viewModelModule = module {
	viewModel { ListNotesViewModel(get()) }
	viewModel { NoteViewModel(get(), get(), get(), get(), get(), get()) }
}