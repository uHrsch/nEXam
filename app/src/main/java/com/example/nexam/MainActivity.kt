package com.example.nexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.widget.*
import java.text.DecimalFormat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        loadView()
    }

    private fun loadView() {
        registerButton(R.id.createExamButton, R.layout.create_exam)
        registerButton(R.id.back, R.layout.dashboard)
        registerButton(R.id.save, R.layout.exam_success)
        registerButton(R.id.toDashboard, R.layout.dashboard)
        registerButton(R.id.editExam, R.layout.create_exam)
        registerButton(R.id.showExam, R.layout.exam_view)
        fillList(R.id.exam_list, R.array.test_exams)
        fillList(R.id.content_list, R.array.test_content)
        addTimer()
    }

    private fun registerButton(button: Int, view: Int) {
        val button = findViewById<Button>(button) ?: return
        button.setOnClickListener {
            setContentView(view)
            loadView()
        }
    }

    private fun fillList(list: Int, array: Int) {
        val examListView: ListView = findViewById(list) ?: return
        examListView.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                resources.getStringArray(array)
            )

        examListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
                setContentView(R.layout.exam_view)
                loadView()
            }
    }

    private fun addTimer() {
        val button = findViewById<Button>(R.id.startTimer) ?: return
        button.setOnClickListener {
            setContentView(R.layout.dashboard)
            loadView()
            startTimeCounter()
        }
    }

    private fun startTimeCounter() {
        val countTime: EditText = findViewById(R.id.countTime)
        //TODO take input as formatted text
        //TODO exception handling
        //TODO stop timer instead of start timer

         var enteredTime: Long = if (TextUtils.isEmpty(countTime.text)) {
             1200000
         } else {
             countTime.text.toString().toLong()
         }

        object : CountDownTimer(enteredTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60

                countTime.setText(
                    getString(
                        R.string.countTimeText,
                        f.format(hour),
                        f.format(min),
                        f.format(sec)
                    )
                )
                counter++
                countTime.isEnabled = false
            }

            override fun onFinish() {
                countTime.setText(getString(R.string.countTimeFinished))
                countTime.isEnabled = true
            }
        }.start()
    }
}