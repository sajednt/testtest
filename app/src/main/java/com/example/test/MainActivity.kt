package com.example.test

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.PathInterpolator
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.LinearInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.test.adapter.MainAdapter1
import com.example.test.databinding.ActivityMainBinding
import com.example.test.datamodel.GetUserUseCase
import com.example.test.datamodel.Member
import com.example.test.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    lateinit var adapter: MainAdapter1
    private var listMain: MutableList<List<Member>> = mutableListOf()

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = MainAdapter1(listMain, this , false)
        adapter.setOnClickListener(object : MainAdapter1.OnItemClickListener {
            override fun onItemClick(member: Member, position: Int) {

                var temp = listMain.size

                if (position + 1 < listMain.size) {
                    listMain.removeIf { listMain.indexOf(it) > position }
                    adapter.notifyItemRangeRemoved(position + 1, temp - (position - 1))
                }

                if (member.team.isNotEmpty() && member.isSelected) {

                    member.team.forEach { it.isSelected = false }
                    adapter.addItem(member.team)

                    lifecycleScope.launch {
                        delay(500)
                        runOnUiThread {
                            binding.recyclerItems.smoothScrollToPosition(listMain.size - 1)
                        }
                    }
                } else if( member.isSelected )  {
                    Toast.makeText(applicationContext, "عضوی یافت نشد", Toast.LENGTH_SHORT).show()
                }

            }
        })
        binding.recyclerItems.adapter = adapter

        viewModel.user.observe(this) { user ->
            if (user != null) {
                binding.user = user
                binding.executePendingBindings()

                lifecycleScope.launch {
                    delay(500)
                    listMain.add(user.team)
                    adapter.updateList(listMain)
                }
            }
        }
        viewModel.loadUser()


        val myInterpolator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PathInterpolator(0.5f, 0.5f, 0.1f, 1.0f)
        } else {
            LinearInterpolator()
        }


        binding.layoutHeader.post {
            ObjectAnimator.ofFloat(binding.layoutHeader, "translationY", (-binding.layoutHeader.height).toFloat(), 0F).apply {
                interpolator = myInterpolator as TimeInterpolator?
                duration = 1000
                start()
            }
        }


        binding.memberlayout.post {
            ObjectAnimator.ofFloat(binding.memberlayout, "translationY",
                (-binding.memberlayout.height).toFloat(), 0F).apply {
                interpolator = myInterpolator as TimeInterpolator?
                duration = 1500
                start()
            }
        }

        binding.switchStyled.setOnCheckedChangeListener { buttonView, isChecked ->

            var new = if(isChecked) true else false
            val animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_fade)
            binding.recyclerItems.startAnimation(animation)
            lifecycleScope.launch {

                delay(800)
                adapter = MainAdapter1(listMain, applicationContext , new)
                adapter.setOnClickListener(object : MainAdapter1.OnItemClickListener {
                    override fun onItemClick(member: Member, position: Int) {
                        var i = if(member.isSelected) 1 else 0

                        var temp = listMain.size

                        if (position + 1 < listMain.size) {
                            listMain.removeIf { listMain.indexOf(it) > position }
                            adapter.notifyItemRangeRemoved(position + 1, temp - (position - 1))
                        }

                        if (member.team.size > 0  && i == 1) {

                            member.team.forEach { it.isSelected = false }
                            adapter.addItem(member.team)
                            lifecycleScope.launch {
                                delay(500)
                                binding.recyclerItems.smoothScrollToPosition(listMain.size - 1)
                            }
                        } else  if( i == 1 ) {
                            Toast.makeText(applicationContext, "عضوی یافت نشد", Toast.LENGTH_SHORT).show()
                        }

                    }
                })
                binding.recyclerItems.adapter = adapter

                val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_in_fade)
                binding.recyclerItems.startAnimation(animation)
            }
        }


    }

}