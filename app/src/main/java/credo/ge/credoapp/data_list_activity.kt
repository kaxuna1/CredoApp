package credo.ge.credoapp

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ListView
import credo.ge.credoapp.views.CredoExtendActivity
import credo.ge.credoapp.views.ListAdapters.ReflectionAdapterAdapter
import kotlinx.android.synthetic.main.activity_data_list.*
import java.util.*

class data_list_activity : CredoExtendActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val context = this;

        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_data_list)
        makeDrawer();
        val listView = findViewById(R.id.listView) as ListView

        val extras = intent.extras
        val classname: Class<*> = extras.getSerializable("class") as Class<*>
        val nameFieldMethodName = extras.getString("nameFieldMethodName")

        val method = classname.getMethod("getData")


        backBtn.setOnClickListener {
            finish()
        }

        var dataToLoad: List<Any>? = null
        val uuid = UUID.randomUUID().toString()

        var adapter: ReflectionAdapterAdapter? = null

        val refreshFunc = fun() {
            dataToLoad = method.invoke(null) as List<Any>
            adapter = ReflectionAdapterAdapter(dataToLoad!!, context, nameFieldMethodName, true, classname, 34f)
            listView.adapter = adapter
        }

        refreshFunc()

        StaticData.comboBoxUpdateFunctions.put(uuid, refreshFunc)

        listView.setOnItemClickListener { parent, view, position, id ->

            val data = dataToLoad!!.get(position)

            var dialogBody2 = layoutInflater.inflate(R.layout.sub_list_prompt_layout, null);

            val builder = AlertDialog.Builder(listView.context).setView(dialogBody2).create()

            (dialogBody2.findViewById(R.id.cancelButton) as Button).setOnClickListener {
                builder.hide()
            }
            (dialogBody2.findViewById(R.id.deleteButton) as Button).setOnClickListener {
                var item = data
                classname.getMethod("delete").invoke(item)
                builder.hide()
                refreshFunc()
            }
            (dialogBody2.findViewById(R.id.editButton) as Button).setOnClickListener {
                val item = data

                val currentId = classname.getMethod("getId").invoke(item) as Long
                val intent = Intent(applicationContext, DataFillActivity::class.java)
                intent.putExtra("class", classname)
                intent.putExtra("autosave",true)
                intent.putExtra("updaterUUID", uuid)
                intent.putExtra("id", currentId)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(intent)
                builder.hide()

            }
            builder.show()


        }
        listView.setOnItemLongClickListener { parent, view, position, id ->

            val data = dataToLoad!!.get(position)
            val intent = Intent(context, DataFillActivity::class.java)
            intent.putExtra("class", classname)
            intent.putExtra("updaterUUID", uuid)
            var currentId = classname.getMethod("getId").invoke(data) as Long
            intent.putExtra("id", currentId)
            context.startActivity(intent)

            return@setOnItemLongClickListener true;
        }
    }
}
