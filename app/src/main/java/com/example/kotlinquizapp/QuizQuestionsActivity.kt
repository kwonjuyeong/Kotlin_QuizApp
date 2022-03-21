package com.example.kotlinquizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList:ArrayList<Question>?=null
    private var mSelectedOptionPosition : Int = 0


    private var progressBar: ProgressBar?=null
    private var tvProgress: TextView? = null
    private var tvQuestion:TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null
    private var btnSubmit : Button ?=null

    //메모 : view binding 사용해서 더 쉽게 만들어보기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)

        tvOptionOne?.setOnClickListener (this)
        tvOptionTwo?.setOnClickListener (this)
        tvOptionThree?.setOnClickListener (this)
        tvOptionFour?.setOnClickListener (this)
        btnSubmit?.setOnClickListener(this)

        mQuestionList = Constants.getQuestions()

        setQuestion()

        defaultOptionsView()

    }


    //main에 있던 코드를 선택 - 리팩터 - Function으로 함수를 만들어줘서 간단하게 사용할 수 있게 만듬.
    //문제를 세팅해주는 함수
    private fun setQuestion() {
        //defaultOptionView로 처음 문제를 불러올 때 answerView함수에서 지정된 색을 없애줌ㅇㅂ
        defaultOptionsView()
        val question: Question? = mQuestionList!![mCurrentPosition - 1]

        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question!!.question
        ivImage?.setImageResource(question.image)
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if(mCurrentPosition == mQuestionList!!.size){
            btnSubmit?.text = "FINISH"
        }else{
            btnSubmit?.text = "SUBMIT"

        }
    }

    //선택이 되지 않았을 때 보여지는 뷰
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        tvOptionOne?.let{
            options.add(0, it)
        }
        tvOptionTwo?.let{
            options.add(1, it)
        }
        tvOptionThree?.let{
            options.add(2, it)
        }
        tvOptionFour?.let{
            options.add(3, it)
        }
        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)

        }
    }

    //option이 눌렸을 때 보여지는 것
    private fun selectedOptionView(tv:TextView, selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    //option 버튼이 눌렸을 때 동작
    override fun onClick(view: View?) {

        when(view?.id){
            R.id.tv_option_one -> {
                tvOptionOne?.let{
                 selectedOptionView(it,1)
                }
            }
            R.id.tv_option_two -> {
                tvOptionTwo?.let{
                    selectedOptionView(it,2)
                }
            }
            R.id.tv_option_three -> {
                tvOptionThree?.let{
                    selectedOptionView(it,3)
                }
            }
            R.id.tv_option_four -> {
                tvOptionFour?.let{
                    selectedOptionView(it,4)
                }
            }
            R.id.btn_submit -> {
                //SelectedOption 초기값은 0이기 때문에 0으로 설정
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++
                    when{

                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }else -> {
                        //지정된 문제가 다 끝났을 때
                        Toast.makeText(this, "You made it to the end", Toast.LENGTH_SHORT).show()

                        }
                    }
                }else{
                    val question = mQuestionList?.get(mCurrentPosition -1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    //다음 문제로 이동하는 버튼. 끝나면 Finish를 띄어줌.
                    if(mCurrentPosition == mQuestionList!!.size){
                        btnSubmit?.text = "FINISH"
                    }else{
                        btnSubmit?.text = "Go To Next Level"
                    }
                    //다시 0으로 초기화해줘야함.
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer:Int, drawableView:Int){
        when(answer){
            1->{
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2->{
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3->{
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4->{
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }

    }
}
