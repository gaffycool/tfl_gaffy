package com.example.tfl_gaffy.presentation.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : BasePresenter.View> {

    var view: T? = null
    private lateinit var compositeDisposable: CompositeDisposable

    protected fun disposeOnViewDetached(disposable: Disposable) {
        if (!this::compositeDisposable.isInitialized) {
            compositeDisposable = CompositeDisposable()
        }

        compositeDisposable.add(disposable)
    }

    @CallSuper
    open fun onViewAttached(view: T) {
        if (this.view != null) {
            throw IllegalStateException("View is already attached!")
        } else {
            this.view = view
        }
    }

    @CallSuper
    fun onViewDetached() {
        if (this::compositeDisposable.isInitialized) {
            compositeDisposable.clear()
        }

        view = null
    }

    open fun isViewAttached(): Boolean {
        return view != null
    }

    interface View
}