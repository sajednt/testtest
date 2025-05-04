package com.example.test

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.test.databinding.ActivityMainBinding
import android.view.animation.AnimationUtils
import android.view.animation.PathInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.IntegerRes
import androidx.core.animation.LinearInterpolator
import com.example.test.adapter.MainAdapter
import com.example.test.adapter.MemberAdapter
import com.example.test.datamodel.GetUserUseCase
import com.example.test.datamodel.Member
import com.example.test.datamodel.User
import com.example.test.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    lateinit var b: ActivityMainBinding
    private val getUserUseCase = GetUserUseCase()
    private val viewModel: UserViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    @Inject
    lateinit var mainAdapterFactory: MainAdapter.Factory
    private var listMember: List<User> = emptyList()

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        b = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = mainAdapterFactory.create(listener = object : MainAdapter.OnItemClickListener {
            override fun onItemClick(member: Member) {
                Toast.makeText(applicationContext, member.username, Toast.LENGTH_SHORT).show()
            }
        })

        b.recyclerItems.adapter = adapter


        viewModel.user.observe(this) { user ->
            if(user != null) {
                b.user = user
                listMember += user
                b.executePendingBindings()
                Log.e("size", listMember.size.toString())
                adapter.submitList(listMember)
            }
        }

        viewModel.loadUser()

//        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
//        b.layoutHeader.startAnimation(slideDown)
//        b.layoutHeader.visibility = View.VISIBLE
//
//        val myInterpolator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            PathInterpolator(0.5f, 0.7f, 0.1f, 1.0f)
//        } else {
//            LinearInterpolator()
//        }

        ObjectAnimator.ofFloat(b.layoutHeader, "translationY", -100f, 0F).apply {
            interpolator = AccelerateInterpolator()
            duration = 2000
            start()
        }



    }

}