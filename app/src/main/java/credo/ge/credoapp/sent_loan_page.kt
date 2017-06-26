package credo.ge.credoapp

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import credo.ge.credoapp.models.Loan

class sent_loan_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent_loan_page)


        val extras = intent.extras
        val id = extras.getLong("id")
        val loan = Loan.getById(id);


        val linear = findViewById(R.id.linear) as LinearLayout



        val txt1 = TextView(applicationContext)
        txt1.text = loan.person.fullName()
        linear.addView(txt1)

        val txt2 = TextView(applicationContext)
        txt2.text = "პირადი #: ${loan.person.personalNumber}"
        linear.addView(txt2)

        val txt3 = TextView(applicationContext)
        txt3.text = "სტატუსი: ${loan.getStatus()}"
        linear.addView(txt3)

        val txt4 = TextView(applicationContext)
        txt4.text = loan.person.fullName()
        linear.addView(txt4)

        val txt5 = TextView(applicationContext)
        txt5.text = loan.person.fullName()
        linear.addView(txt5)

        val txt6 = TextView(applicationContext)
        txt6.text = loan.person.fullName()
        linear.addView(txt6)

        val txt7 = TextView(applicationContext)
        txt7.text = loan.person.fullName()
        linear.addView(txt7)

        val txt8 = TextView(applicationContext)
        txt8.text = loan.person.fullName()
        linear.addView(txt8)

        val txt9 = TextView(applicationContext)
        txt9.text = loan.person.fullName()
        linear.addView(txt9)




        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
