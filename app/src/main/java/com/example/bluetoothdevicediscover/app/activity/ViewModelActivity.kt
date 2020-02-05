package com.example.bluetoothdevicediscover.app.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bluetoothdevicediscover.app.viewAction.BaseViewAction
import com.example.bluetoothdevicediscover.app.viewState.BaseViewState
import com.example.bluetoothdevicediscover.app.viewmodel.BaseViewModel
import com.example.bluetoothdevicediscover.app.viewmodel.provider.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class ViewModelActivity<VM : BaseViewModel<VS, VA>, VS : BaseViewState, VA : BaseViewAction>
    : AppCompatActivity() {

    protected lateinit var viewModel: VM

    abstract val viewModelType: Class<VM>

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelType)

        viewModel.actionsLiveData.observe(this, Observer { handleNewAction(it) })
        viewModel.baseActionsLiveData.observe(this, Observer { handleBaseAction(it) })
        viewModel.stateLiveData.observe(this, Observer { renderFreshViewState(it) })
    }

    // Method to draw a new state (to be overriden for each children activities)
    abstract fun renderFreshViewState(newViewState: VS)

    // Method to handle new action from viewmodel to ui
    abstract fun handleNewAction(action: VA)

    // Method to handle base action shared among view models. This is util to avoid
    // creating multiple view actions that do de same. For example, showing a toast message
    private fun handleBaseAction(baseAction: BaseViewAction) {
        when (baseAction) {
            is BaseViewAction.ShowToast -> Toast.makeText(this, baseAction.toastText, baseAction.toastLength).show()
        }
    }
}
