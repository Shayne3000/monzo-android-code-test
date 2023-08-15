package com.monzo.androidtest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ThreadingModule {

    @Provides
    @Named(IO)
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named(UI)
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    companion object{
        const val IO = "io"
        const val UI = "ui"
    }
}
