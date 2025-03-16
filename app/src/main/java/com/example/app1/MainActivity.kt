package com.example.app1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var currentText: TextView
    lateinit var previousText: TextView
    var currentOperation: String = ""
    var firstValue: Int? = null
    var secondValue: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        textHello.setTextColor(0xffff0000.toInt())// vì ffff0000 lớn hơn int nên lỗi
//        val n = 100
//        textHello.setText(n.toString())
//        resources.getFont()
        currentText = findViewById(R.id.text)
        previousText = findViewById(R.id.textView)
        val button = listOf( R.id.button9, R.id.button6,
            R.id.button3, R.id.button7,
            R.id.button8, R.id.button4, R.id.button5, R.id.button1, R.id.button2, R.id.button0)
        for(id in button){
            findViewById<Button>(id).setOnClickListener {
                handleNumberClick(it as Button)
                }
        }
        findViewById<Button>(R.id.buttonPlus).setOnClickListener{ handleOperationClick("+") }
        findViewById<Button>(R.id.buttonMinus).setOnClickListener{ handleOperationClick("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener{ handleOperationClick("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener{ handleOperationClick("/") }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener{ handleOperationClick("=") }
        findViewById<Button>(R.id.buttonCE).setOnClickListener{ handleFunctionClick("CE") }
        findViewById<Button>(R.id.buttonC).setOnClickListener{ handleFunctionClick("C") }
        findViewById<Button>(R.id.buttonBS).setOnClickListener{ handleFunctionClick("BS") }




//        currentText.text = HtmlCompat.fromHtml("<b>xin chao<b>", HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
    private fun getResult(): Int?{
        return  when(currentOperation){
            "+" -> firstValue?.plus(secondValue.toIntOrNull() ?: 0)
            "-" -> firstValue?.minus(secondValue.toIntOrNull() ?: 0)
            "*" -> firstValue?.times(secondValue.toIntOrNull() ?: 1)
            "/" -> {
                val divisor = secondValue.toIntOrNull()
                if (divisor == null || divisor == 0) {
                    currentText.text = "Lỗi"
                    firstValue = null
                    secondValue = ""
                    return null
                } else {
                    firstValue?.div(divisor)
                }
            }
            "=" -> firstValue
            else -> firstValue
        }
    }

    private fun handleOperationClick(operation: String){

        if (firstValue == null) return

        if (secondValue.isNotEmpty()) {
            firstValue = getResult()
            secondValue = ""
            currentText.text = formatNumber(firstValue ?: 0)
        }

        currentOperation = if (operation == "=") "" else operation
        previousText.text = "$firstValue $currentOperation"

    }

    private fun handleNumberClick(numberButton: Button){

        val number = numberButton.text.toString()
        if (currentText.text.toString() == "0") {
            currentText.text = number
        } else {
            currentText.text = currentText.text.toString() + number
        }

        if (currentOperation.isEmpty()) {
            firstValue = currentText.text.toString().toInt()
        } else {
            secondValue = currentText.text.toString()
        }
    }

    private fun handleFunctionClick(function: String){

        when (function) {
            "CE" -> {
                currentText.text = "0"
                secondValue = ""
            }
            "C" -> {
                currentText.text = "0"
                previousText.text = ""
                firstValue = null
                secondValue = ""
                currentOperation = ""
            }
            "BS" -> {
                val text = currentText.text.toString()
                currentText.text = if (text.length > 1) text.dropLast(1) else "0"
                if (currentOperation.isEmpty()) {
                    firstValue = currentText.text.toString().toInt()
                } else {
                    secondValue = currentText.text.toString()
                }
            }
        }
    }

    private fun formatNumber(value: Int): String {
        return value.toString()
    }

}