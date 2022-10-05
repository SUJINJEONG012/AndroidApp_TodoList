package com.example.todolist_app

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MyAdapter

    var datas:MutableList<String>? = null
    var datas2:MutableList<String>? = null
    var datas3:MutableList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //requestLauncher 생성 => 사후처리가 필요할 때  (권장) 안드로이드 11부터 권장됨
        //requestLauncher 생성
        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            Log.d("myLog", "${it.data?.getStringExtra("result")}")
            it.data!!.getStringExtra("result")?.let {
                Log.d("myLog", "${it}")
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }

            it.data!!.getStringExtra("result2")?.let {
                Log.d("myLog", "${it}")
                datas2?.add(it)
                adapter.notifyDataSetChanged()
            }

            it.data!!.getStringExtra("result3")?.let {
                Log.d("myLog", "${it}@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                datas3?.add(it)
                adapter.notifyDataSetChanged()
            }

        }


        //mainFab 클릭 이벤트
        binding.mainFab.setOnClickListener {
            //addActivity로 인테트에 담아서 시스템에 전달
            val intent = Intent(this, AddActivity::class.java)
            requestLauncher.launch(intent)
        }

        //DB에서 데이터를 가져온다.
        datas = mutableListOf()
        datas2 = mutableListOf()
        datas3 = mutableListOf()

        val db = DBHelper(this).writableDatabase
        val cursor = db.rawQuery("select * from TODO_TB",null)
        cursor.run {
            while (moveToNext()){
                datas?.add(cursor.getString(1))
                datas2?.add(cursor.getString(2))
                datas3?.add(cursor.getString(3))

            }
        }


        //리사이클러뷰 화면구성
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(datas!!,datas2!!,datas3!!)

        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

    }
}