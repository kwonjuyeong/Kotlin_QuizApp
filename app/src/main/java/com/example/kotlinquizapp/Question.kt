package com.example.kotlinquizapp

//데이터 클래스는 말 그대로 데이터가 어떤 것이 들어가는가를 정의하는 클래스이다.
data class Question(
    val id:Int,
    val question: String,
    val image: Int,
    val optionOne:String,
    val optionTwo:String,
    val optionThree:String,
    val optionFour:String,
    val correctAnswer: Int,
)
