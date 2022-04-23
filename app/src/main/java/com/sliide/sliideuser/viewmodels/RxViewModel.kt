package com.sliide.sliideuser.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
open class RxViewModel: ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}