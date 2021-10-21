package com.example.headsup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.headsup.adapters.RVAdapter
import com.example.headsup.database.Celebrity
import com.example.headsup.database.DatabaseHandler

class Data : AppCompatActivity() {
    private lateinit var dbHandler: DatabaseHandler

    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var etName: EditText
    private lateinit var etTaboo1: EditText
    private lateinit var etTaboo2: EditText
    private lateinit var etTaboo3: EditText
    private lateinit var btAdd: Button
    private lateinit var btUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        dbHandler = DatabaseHandler(this)

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(this, dbHandler.getCelebrities())
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        etName = findViewById(R.id.etName)
        etTaboo1 = findViewById(R.id.etTaboo1)
        etTaboo2 = findViewById(R.id.etTaboo2)
        etTaboo3 = findViewById(R.id.etTaboo3)
        btAdd = findViewById(R.id.btAdd)
        btAdd.setOnClickListener { addCelebrity() }

        btUpdate = findViewById(R.id.btUpdate)
    }

    private fun addCelebrity(){
        if(etName.text.isNotBlank() && etTaboo1.text.isNotBlank() && etTaboo2.text.isNotBlank() &&
                etTaboo3.text.isNotBlank()){
            dbHandler.addCelebrity(etName.text.toString(), etTaboo1.text.toString(), etTaboo2.text.toString(), etTaboo3.text.toString())
            Toast.makeText(this, "Celebrity added", Toast.LENGTH_LONG).show()
            rvAdapter.update(dbHandler.getCelebrities())
        }else{
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show()
        }
    }

    fun display(update: Boolean){
        if(update){
            btAdd.isVisible = false
            btUpdate.isVisible = true
        }else{
            btAdd.isVisible = true
            btUpdate.isVisible = false
        }
    }

    fun updateCelebrity(celebrity: Celebrity){
        display(true)
        etName.setText(celebrity.name)
        etTaboo1.setText(celebrity.taboo1)
        etTaboo2.setText(celebrity.taboo2)
        etTaboo3.setText(celebrity.taboo3)
        btUpdate.setOnClickListener {
            if(etName.text.isNotBlank() && etTaboo1.text.isNotBlank() && etTaboo2.text.isNotBlank() &&
                etTaboo3.text.isNotBlank()){
                dbHandler.updateCelebrity(
                    Celebrity(celebrity.id,
                        etName.text.toString(),
                        etTaboo1.text.toString(),
                        etTaboo2.text.toString(),
                        etTaboo3.text.toString()))
                Toast.makeText(this, "Celebrity updated", Toast.LENGTH_LONG).show()
                display(false)
                this@Data.recreate()
            }else{
                Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteCelebrity(celebrity: Celebrity){
        dbHandler.deleteCelebrity(celebrity)
        Toast.makeText(this, "Celebrity Deleted", Toast.LENGTH_LONG).show()
        this@Data.recreate()
    }
}