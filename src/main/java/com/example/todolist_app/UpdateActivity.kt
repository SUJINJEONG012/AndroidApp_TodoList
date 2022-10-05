package com.example.todolist_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.todolist_app.databinding.ActivityUpdateBinding


class UpdateActivity : AppCompatActivity() {
    lateinit var binding:ActivityUpdateBinding
    lateinit var todo:String
    lateinit var todo2:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        todo = intent.getStringExtra("todo").toString()
        todo2 = intent.getStringExtra("todo2").toString()
        binding.updateEditView.setText(todo)
        binding.updateEditView2.setText(todo2)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_update , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_update_save -> {
                val inputData = binding.updateEditView.text.toString()
                val inputData2 = binding.updateEditView2.text.toString()

                if (inputData.isBlank()){
                    //할일 문자열이 비어져 있을때
                    Toast.makeText(this,"할일이 비어져 있습니다. 확인 해주세요.",Toast.LENGTH_SHORT).show()
                    return false
                }else{
                    //할일 문자열이 있을때
                    val db = DBHelper(this).writableDatabase
                    db.execSQL("update TODO_TB set todo = ?, todo2  = ?  where todo = ? ", arrayOf(inputData,inputData2, todo))
                    db.close()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}