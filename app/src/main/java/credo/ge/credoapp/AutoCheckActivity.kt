package credo.ge.credoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_auto_check.*

class AutoCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_check)
        backBtn.setOnClickListener {
            finish()
        }
        btn_search.setOnClickListener {
            val id = idNumber.text
        }
    }
}
