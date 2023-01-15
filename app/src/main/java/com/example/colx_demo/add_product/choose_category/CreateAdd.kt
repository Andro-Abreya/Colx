package com.example.colx_demo.add_product.choose_category

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colx_demo.R
import com.example.colx_demo.add_product.AddProduct
import com.example.colx_demo.add_product.choose_subcategory.ChooseSubCategory

class CreateAdd : AppCompatActivity() {

    private lateinit var catList:Array<String>
    private lateinit var catImgList:Array<Int>
    private lateinit var catData: ArrayList<CategoryDC>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_add)

        supportActionBar?.title = "What are you offering?"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val hashMap : HashMap<String, ArrayList<String>>
                = HashMap (4)

        //adding elements to the hashMap using put() function
        hashMap["Sports Equipments"] = arrayListOf("Cricket","Badminton","VolleyBall","Tennis","Football")
        hashMap["Electronics"] = arrayListOf("Laptop Components","Chargers","Pen drives","Headphones","Extension Boards")
        hashMap["Bedding"] = arrayListOf("Bedsheet","Pillow","Mattress")
        hashMap["Bath Essentials"] = arrayListOf("Bucket","Mug")


        catList = arrayOf("Books","Sports Equipments","Electronics","Bedding","Bath Essentials")
        catImgList = arrayOf(R.drawable.books,R.drawable.sports,R.drawable.electronics,R.drawable.bed,R.drawable.bath_essentials)

        val catRv = findViewById<RecyclerView>(R.id.chooseCatRv)
        catRv.layoutManager = GridLayoutManager(this,2)
        catRv.setHasFixedSize(true)

        catData = arrayListOf()
        getCatData()
        val adapter = CategoryAdapter(catData)
        catRv.adapter = adapter
        adapter.setOnItemClickListener(object: CategoryAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                if(catData[position].title == "Books"){
                    startActivity(Intent(this@CreateAdd,AddProduct::class.java)
                        .putExtra("Category","Books"))
                }
                else{
                    val intent = Intent(this@CreateAdd,ChooseSubCategory::class.java)
                    intent.putExtra("Category",catData[position].title)
                    intent.putExtra("SubCategory List",hashMap[catData[position].title])
                    startActivity(intent)
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
    private fun getCatData() {

        for(i in catImgList.indices){
            val category = CategoryDC(catImgList[i],catList[i])
            catData.add(category)
        }

    }
}