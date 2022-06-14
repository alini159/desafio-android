package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.data.database.UserDao
import com.picpay.desafio.android.data.database.UserDatabase
import com.picpay.desafio.android.data.local.UserLocalDataSourceImp
import com.picpay.desafio.android.data.remote.UserRemoteDataSourceImp
import com.picpay.desafio.android.data.UserRepositoryImp
import com.picpay.desafio.android.presenter.viewmodel.UserListViewModel
import com.picpay.desafio.android.data.apiclient.PicPayService
import com.picpay.desafio.android.domain.interfaces.UserLocalDataSource
import com.picpay.desafio.android.domain.interfaces.UserRemoteDataSource
import com.picpay.desafio.android.domain.interfaces.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//colocar em arquivo de constants
const val KEY = "key"
private const val DB_NAME = "user.db"

val retrofitModule = module {
    single<Retrofit> {
        val property = getProperty<String>(KEY)
        Retrofit.Builder()
            .baseUrl(property)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<PicPayService> { get<Retrofit>().create(PicPayService::class.java) }

    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}

val databaseModule = module {
    single<UserDatabase> {
        Room.databaseBuilder(
            get(),
            UserDatabase::class.java,
            DB_NAME
        ).build()
    }
}

val daoModule = module {
single<UserDao> { get<UserDatabase>().userDao()}
}

val viewModule = module {
    viewModel { UserListViewModel(get()) }
}

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImp(get(), get()) }
}

val localModule = module {
    single<UserLocalDataSource> { UserLocalDataSourceImp(get())}
}

val remoteModule = module {
    single<UserRemoteDataSource> { UserRemoteDataSourceImp(get())}
}

val appModules = listOf(
    retrofitModule,
    viewModule,
    repositoryModule,
    daoModule,
    databaseModule,
    localModule,
    remoteModule
)

