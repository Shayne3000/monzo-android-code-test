package com.monzo.androidtest.util

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
