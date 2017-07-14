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
import android.content.Intent



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
        txt1.setTextColor(R.color.blackk)
        linear.addView(txt1)

        val txt2 = TextView(applicationContext)
        txt2.text = "პირადი #: ${loan.person.personalNumber}"
        txt2.setTextColor(R.color.blackk)
        linear.addView(txt2)

        val txt3 = TextView(applicationContext)
        txt3.text = "სტატუსი: ${loan.getStatus()}"
        txt3.setTextColor(R.color.blackk)
        linear.addView(txt3)

        val txt4 = TextView(applicationContext)
        txt4.text = "სესხის თანხა: ${loan.amount}"
        txt4.setTextColor(R.color.blackk)
        linear.addView(txt4)

        val txt5 = TextView(applicationContext)
        txt5.text = "სესხის ვალუტა: ${loan.currency.name}"
        txt5.setTextColor(R.color.blackk)
        linear.addView(txt5)

        val txt6 = TextView(applicationContext)
        txt6.text = "სესხის ვადა: ${loan.period}"
        txt6.setTextColor(R.color.blackk)
        linear.addView(txt6)

        val txt7 = TextView(applicationContext)
        txt7.text = "სესხის პროცენტი: ${loan.intRate}"
        txt7.setTextColor(R.color.blackk)
        linear.addView(txt7)
        val txt11 = TextView(applicationContext)
        txt11.text = "სესხის ეფექტური პროცენტი: ${loan.effectivePercent}"
        txt11.setTextColor(R.color.blackk)
        linear.addView(txt11)

        val txt8 = TextView(applicationContext)
        txt8.text = "გაცემის საკომისიო: ${loan.loanCommission}"
        txt8.setTextColor(R.color.blackk)
        linear.addView(txt8)

        val txt9 = TextView(applicationContext)
        txt9.text = "სმს მომსახურების საკომისიო: ${loan.smsCommission}"
        txt9.setTextColor(R.color.blackk)
        linear.addView(txt9)

        val txt10 = TextView(applicationContext)
        txt10.text = "ყოველთვიური შენატანი თანხა: ${loan.payment}"
        txt10.setTextColor(R.color.blackk)
        linear.addView(txt10)
        val txt12 = TextView(applicationContext)
        txt12.text = "სტატუსის თარიღი: ${loan.statusChangeDate}"
        txt12.setTextColor(R.color.blackk)
        linear.addView(txt12)

    /*    val txt7 = TextView(applicationContext)
        txt7.text = "სესხის თანხა: ${loan.amount}"
        linear.addView(txt7)*/
/*

        val txt8 = TextView(applicationContext)
        txt8.text = loan.person.fullName()
        linear.addView(txt8)

        val txt9 = TextView(applicationContext)
        txt9.text = loan.person.fullName()
        linear.addView(txt9)
*/




        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onBackPressed() {
        val startMain = Intent(this,ScrollingMainActivity::class.java)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startMain.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(startMain)
    }

}
