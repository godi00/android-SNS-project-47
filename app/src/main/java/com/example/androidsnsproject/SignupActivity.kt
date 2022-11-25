package com.example.androidsnsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidsnsproject.databinding.ActivitySignupBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding // binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // btnSignup: 회원가입 수행
        binding.btnSignup.setOnClickListener {
            // editInputEmail: 이메일 입력 텍스트 창
            val userEmail = binding.editInputEmail.text.toString()
            // editInputPwd: 비밀번호 입력 텍스트 창
            val password = binding.editInputPwd.text.toString()
            // editInputName: 이름 입력 텍스트 창
            val userName = binding.editInputName.text.toString()
            // editInputBirth: 생일 입력 텍스트 창
            val userBirth = binding.editInputBirth.text.toString()

            // 입력한 정보를 토대로 회원가입 수행
            doSignIn(userEmail, password, userName, userBirth)
        }
    }

    // 회원가입 함수
    private fun doSignIn(userEmail: String, password: String, userName: String, birth: String) {
        // 사용자 생성
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener {
                // 이메일 유효성 검사
                val correctEmail = isEmail(userEmail)
                // 비밀번호 유효성 검사
                val correctPwd = isPwd(password)
                // 이름 유효성 검사
                val correctName = isName(userName)
                // 생년월일 유효성 검사
                val correctBirth = isBirth(birth)

                // 모든 게 정상적으로 수행되어야만 회원가입 완료
                if(it.isSuccessful && correctEmail && correctPwd && correctName && correctBirth) {
//                    println("sign-up success")
//                    println(Firebase.auth.currentUser?.uid)
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                } else {
//                    println("sign-up failed ${it.exception?.messㅁㄴㅇage}")
                    Log.w("SignupActivity", "signUpWithEmail", it.exception)
                    if(!correctEmail)
                        Toast.makeText(this, "이메일 형식을 맞춰 작성해야 합니다.", Toast.LENGTH_SHORT).show()
                    else if(!correctPwd)
                        Toast.makeText(this, "비밀번호는 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                    else if(!correctName)
                        Toast.makeText(this, "이름은 1~10자 내여야 합니다.", Toast.LENGTH_SHORT).show()
                    else if(!correctBirth)
                        Toast.makeText(this, "생년월일 형식을 맞춰 작성해야 합니다.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /*
     * 형식 오류의 기준
     * 이메일: abc@domain.com 과 같은 일반적인 이메일 형식
     * 비밀번호: 6자 이상
     * 이름: 1~10자
     * 생년월일: 0000-00-00 형식 && 1900-01-01 ~ 2022-01-01 출생
     */

    // 이메일 유효성 검사 함수
    private fun isEmail(userEmail: String): Boolean {
        // 이메일 정규식 활용
        val regexEmail = Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        // 형식 검사
        if(userEmail.matches(regexEmail))
            return true // correct
        return false // incorrect
    }

    // 비밀번호 유효성 검사 함수
    private fun isPwd(userPwd: String): Boolean {
        // 6자 이상의 비밀번호
        val regexPwd = Regex("^[A-Za-z0-9]{6}\$")
        // 형식 검사
        if(userPwd.matches(regexPwd))
            return true // correct
        return false // incorrect
    }

    // 이름 유효성 검사 함수
    private fun isName(userName: String): Boolean {
        // 1~10자의 이름
        val regexName = Regex("^[A-Za-z]{1,10}\$")
        // 형식 검사
        if(userName.matches(regexName))
            return true // correct
        return false // incorrect
    }

    // 생년월일 유효성 검사 함수
    private fun isBirth(userBirth: String): Boolean {
        // 0000-00-00 형식
        val regexBirth = Regex("^\\d{4}-\\d{2}-\\d{2}\$")
        // 형식 검사
        if(!userBirth.matches(regexBirth))
            return false // incorrect

        // 1900-01-01 ~ 2022-01-01 외의 숫자 입력 시 오류
        // "-"을 기준으로 문자열 토큰 얻기
        val token = userBirth.split('-')
        val year = token[0].toInt()
        val month = token[1].toInt()
        val day = token[2].toInt()
        // 형식 검사
        if(year in 1900..2022 && month in 1..12) {
            // 2월 (1~28)
            if(month == 2 && day in 1..28)
                return true // correct
            // 1/3/5/7/8/10/12 (1~31)
            else if(month == 1 || month == 3 || month == 5 || month == 7 ||
                    month == 8 || month == 10 || month == 12) {
                if(day in 1..31)
                    return true // correct
            }
            // 4/6/9/11 (1~30)
            else {
                if(day in 1..30)
                    return true // correct
            }
        }
        return false // incorrect
    }
}