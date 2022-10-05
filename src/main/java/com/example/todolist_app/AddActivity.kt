package com.example.todolist_app

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.todolist_app.databinding.ActivityAddBinding


//Add 액티비티 추가
class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add , menu)
        return super.onCreateOptionsMenu(menu)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add_save -> {
                val intent = intent
                val todoText1 = binding.addEditView.text.toString()
                val todoText2 = binding.addEditView2.text.toString()
                val dataTime = binding.addDateView.text.toString()

                if(todoText1.isBlank()){
                    //할일 입력값이 없을때
                    setResult(Activity.RESULT_CANCELED, intent)
                }else{
                    //할일 입력값이 있을때
                    //데이터 베이스 연동해서 inser문 작성
                    val db = DBHelper(this).writableDatabase
                    Log.d("myLog","데이터확인 => ${todoText1}, ${todoText2}")
                    db.execSQL("insert into TODO_TB(todo, todo2) values(?,?)", arrayOf(todoText1,todoText2))
                    // db.execSQL("insert into TODO_TB(todo) values(TEST1,TEST2)")
                    db.close()
                    intent.putExtra("result",todoText1)
                    intent.putExtra("result2",todoText2)
                    intent.putExtra("result3",dataTime)
                    setResult(Activity.RESULT_OK,intent)
                }
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}