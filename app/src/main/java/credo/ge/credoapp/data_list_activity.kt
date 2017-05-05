package credo.ge.credoapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import credo.ge.credoapp.views.CredoExtendActivity
import credo.ge.credoapp.views.ListAdapters.ReflectionAdapterAdapter

class data_list_activity : CredoExtendActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val context=this;

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_list)
        val listView = findViewById(R.id.listView) as ListView

        val extras = intent.extras
        val classname:Class<*> = extras.getSerializable("class") as Class<*>
        val nameFieldMethodName = extras.getString("nameFieldMethodName")

        val method = classname.getMethod("getData")
        val dataToLoad = method.invoke(null) as List<Any>

        var adapter = ReflectionAdapterAdapter(dataToLoad, context, nameFieldMethodName, true, classname,34f)


        listView.adapter=adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val data=dataToLoad.get(position)
            val intent = Intent(context, DataFillActivity::class.java)
            intent.putExtra("class", classname)

            var currentId = classname.getMethod("getId").invoke(data) as Long
            intent.putExtra("id", currentId)
            context.startActivity(intent)
        }
    }
}
