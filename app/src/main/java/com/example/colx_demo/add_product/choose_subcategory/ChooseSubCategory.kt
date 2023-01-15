package com.example.colx_demo.add_product.choose_subcategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colx_demo.R
import com.example.colx_demo.add_product.AddProduct
import com.example.colx_demo.add_product.choose_category.CategoryAdapter
import com.example.colx_demo.add_product.choose_category.CategoryDC

class ChooseSubCategory : AppCompatActivity() {

    private lateinit var subCatData : ArrayList<SubCategoryDC>
    private lateinit var subCategoryList : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_sub_category)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val subCat = intent.getSerializableExtra("SubCategory List") as ArrayList<*>
        val category = intent.getStringExtra("Category")
        supportActionBar?.title = category.toString()
        subCategoryList = subCat.toString().subSequence(1,subCat.toString().length-1).split(",").map { it -> it.trim() }.toTypedArray()
        //Toast.makeText(this,"Category = $category , sub_categories = $subCategoryList",Toast.LENGTH_LONG).show()
        val subCatRv = findViewById<RecyclerView>(R.id.subCatRv)
        subCatRv.layoutManager = LinearLayoutManager(this)
        subCatRv.setHasFixedSize(true)
        subCatData = arrayListOf()
        getSubCatData()

        val adapter = SubCatAdapter(subCatData)
        subCatRv.adapter = adapter
        adapter.setOnItemClickListener(object: SubCatAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                
                //Toast.makeText(this@ChooseSubCategory,"You chose ${subCatData[position].title}",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@ChooseSubCategory,AddProduct::class.java)
                    .putExtra("Category",category)
                    .putExtra("Sub Category",subCatData[position].title))
            }
        })
    }

    private fun getSubCatData() {
        for(i in subCategoryList.indices){
            val category = SubCategoryDC(subCategoryList[i].toString())
            subCatData.add(category)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}