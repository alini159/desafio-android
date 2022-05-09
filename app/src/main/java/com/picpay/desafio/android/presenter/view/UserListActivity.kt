package com.picpay.desafio.android.presenter.view

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presenter.adapter.UserListAdapter
import com.picpay.desafio.android.domain.model.UserResponse
import com.picpay.desafio.android.presenter.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity(R.layout.activity_user_list) {

    private lateinit var recyclerView: RecyclerView
    private val progressBar by lazy {
        findViewById<ProgressBar>(R.id.user_list_progress_bar)
    }

    private lateinit var adapter: UserListAdapter
    private val viewModel: UserListViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        configureRecyclerView()
        getUsers()
    }

    private fun getUsers() {
        loadLocalList()
        loadApiList()
    }

    private fun loadLocalList() {
        viewModel.getUser().observe(this, Observer {
            if (it.isNullOrEmpty()) {
              Toast.makeText(this, "Tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } else {
                adapter.differ.submitList(it)
            }
        })
    }

    private fun loadApiList() {
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is UserResponse.Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this,"voce esta sem internet, tente novamente mais tarde", Toast.LENGTH_LONG).show()
                }
                is UserResponse.Loading -> progressBar.visibility = View.VISIBLE
                is UserResponse.Success -> progressBar.visibility = View.GONE
            }
        })
    }

    private fun configureRecyclerView() {
        recyclerView = findViewById(R.id.user_list_recyclerView)
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
    }
}
