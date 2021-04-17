package com.example.prontoassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.*

class MainActivity : AppCompatActivity() {

    //assumptions:
    //The UI doesn't need to follow an architecture pattern, because the app is very simple
    //String file is not needed because translations will not be used
    //Conformity will be a comparison of the difference between the percent the numbers should show up as to perfectly align with benford's law and what the actual percent is
    //-- We will allow within 10% accuracy
    //-- Small data sets will never be able to conform
    //-- Large data sets will be able to conform, however conforming only means that the number distribution looks similar. It will not actually be accurate statistically for conformity.
    private val benfordCalculator = BenfordCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.smallDataSet1Button).setOnClickListener {
            populateDataEditText("1, 1, 1, 2, 2, 8, 8, 9, 9")
        }
        findViewById<Button>(R.id.smallDataSet2Button).setOnClickListener {
            populateDataEditText("""
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                    3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                    4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                    5, 5, 5, 5, 5, 5, 5, 5,
                    6, 6, 6, 6, 6, 6, 6,
                    7, 7, 7, 7, 7, 7,
                    8, 8, 8, 8, 8,
                    9, 9, 9, 9, 9
            """.trimMargin())
        }
        findViewById<Button>(R.id.smallDataSet3Button).setOnClickListener {
            populateDataEditText("1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 4")
        }

        findViewById<Button>(R.id.calculateBenfordConformityButton).setOnClickListener {
            try {
                val conforms = benfordCalculator.conforms(findViewById<EditText>(R.id.dataEditText).text.toString())
                showConforms(conforms)

            } catch (e: BenfordCalculationException) {
                if (e.errorType == BenfordCalculationErrorType.NUMBER_ZERO) {
                    showToast("Numbers can't be zero")
                } else if (e.errorType == BenfordCalculationErrorType.PARSE_ERROR) {
                    showToast("There was a parse error with the input")
                }
            }
        }
    }

    private fun showConforms(conforms: Boolean) {
        findViewById<LinearLayout>(R.id.conformityLayout).visibility = VISIBLE

        findViewById<TextView>(R.id.valuesTextView).text = findViewById<EditText>(R.id.dataEditText).text.toString()
        val conformsTextView = findViewById<TextView>(R.id.conformsTextView)
        if (conforms) {
            conformsTextView.text = "True"
            conformsTextView.setTextColor(resources.getColor(android.R.color.holo_green_dark))
        } else {
            conformsTextView.text = "False"
            conformsTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun populateDataEditText(data: String) {
        findViewById<EditText>(R.id.dataEditText).setText(data)
    }
}