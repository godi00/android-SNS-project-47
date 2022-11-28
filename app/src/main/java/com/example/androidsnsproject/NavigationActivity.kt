package com.example.androidsnsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidsnsproject.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기본으로 들어가지는 프래그먼트 보이기

        binding.navigationView.setOnItemSelectedListener {
            item -> when(item.itemId) {
                R.id.homeFragment -> {
                    // move to homeFragment
                    val homeFrag = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, homeFrag).commit()
                    true
                }
                R.id.friendFragment -> {
                    // move to friendFragment
                    val friendFrag = FriendFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, friendFrag).commit()
                    true
                }
                R.id.profileFragment -> {
                    // move to profileFragment
                    val profileFrag = ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, profileFrag).commit()
                    true
                }
                R.id.notifyFragment -> {
                    // move to notifyFragment
                    val notifyFrag = NotifyFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, notifyFrag).commit()
                    true
                }
            else -> { false }
            } // when END
        } // setOnItemSelectedListener END
    } // OnCreate END
}