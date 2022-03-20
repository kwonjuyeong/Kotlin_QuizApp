package com.example.kotlinquizapp
//국기, 수도 등
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Tip : snake case와 camel case -> 변수명에 _ 언더바가 들어가면 밑줄이 뜨게된다. 변수명의 처음은 대문자로 쓰면 에러가 사라지게 된다
        //예시 : btn_start = snake case , btnStart = camel case
        val btnStart : Button = findViewById(R.id.btn_start)
        val etName :EditText = findViewById(R.id.et_name)
        btnStart.setOnClickListener {

            if (etName.text.isEmpty()){
                Toast.makeText(this,"Please enter your name", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                startActivity(intent)
                finish()

            }

        }

    }
}