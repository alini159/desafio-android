package com.picpay.desafio.android.presenter.view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.UserResponse.*
import com.picpay.desafio.android.presenter.adapter.UserListAdapter
import com.picpay.desafio.android.presenter.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity(R.layout.activity_user_list) {

    private lateinit var recyclerView: RecyclerView
    private val progressBar by lazy {
        findViewById<ProgressBar>(R.id.user_list_progress_bar)
    }

    private lateinit var adapter: UserListAdapter
    private val viewModel: UserListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRecyclerView()
        loadUserList()
    }

    private fun loadUserList() {
        viewModel.getUser().observe(this, Observer {
            adapter.differ.submitList(it)
        })
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Success -> {
                    progressBar.visibility = View.GONE
                }
                is Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this, getString(R.string.failure), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun configureRecyclerView() {
        recyclerView = findViewById(R.id.user_list_recyclerView)
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
    }
}
