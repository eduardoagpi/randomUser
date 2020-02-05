package com.example.bluetoothdevicediscover.app.activity

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.bluetoothdevicediscover.app.viewAction.RandomUserViewAction
import com.example.bluetoothdevicediscover.app.viewState.RandomUserViewState
import com.example.bluetoothdevicediscover.app.viewmodel.RandomUserViewModel
import kotlinx.android.synthetic.main.activity_random_user.*
import android.content.Intent
import android.net.Uri
import com.example.bluetoothdevicediscover.R


class RandomUserActivity : ViewModelActivity<RandomUserViewModel, RandomUserViewState, RandomUserViewAction>() {

    override val viewModelType = RandomUserViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_user)
        btnFetchFirstUser.setOnClickListener { viewModel.inputActionGetFreshUserAndDisplayIt() }
        btnPrev.setOnClickListener { viewModel.inputActionRequestPreviousUser() }
        btnNext.setOnClickListener { viewModel.inputActionRequestNextUser() }
        btnLocateInMap.setOnClickListener { viewModel.inputActionClickOpenUserLocation() }
        viewModel.actionNotifyStarted()
        viewModel.inputActionGetFreshUserAndDisplayIt()
    }

    override fun renderFreshViewState(newViewState: RandomUserViewState) {
        when(newViewState) {
            is RandomUserViewState.EmptyState -> {
                userDetailsGroup.visibility = View.GONE
                btnPrev.visibility = View.GONE
                noResultsGroup.visibility = View.VISIBLE
            }
            is RandomUserViewState.UserDetailsViewState -> {
                userDetailsGroup.visibility = View.VISIBLE
                noResultsGroup.visibility = View.GONE
                lblName.text = newViewState.displayName
                lblAge.text = newViewState.age
                lblAddress.text = newViewState.address
                lblMail.text = newViewState.email
                lblPhone.text = newViewState.phones
                Glide.with(this).load(newViewState.profilePictureUrl).into(imageUser)
                btnPrev.visibility = if (newViewState.displayPrevious) View.VISIBLE else View.GONE

            }
        }
    }

    override fun handleNewAction(action: RandomUserViewAction) {
        when (action) {
            is RandomUserViewAction.OpenMap -> {
                val gmmIntentUri = Uri.parse("geo:${action.lat},${action.long}")
                //geo:37.7749,-122.4194
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        }
    }
}
