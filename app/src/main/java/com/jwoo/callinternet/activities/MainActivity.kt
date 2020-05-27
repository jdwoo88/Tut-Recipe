package com.jwoo.callinternet.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jwoo.callinternet.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener {
            var intent: Intent = Intent(this, RecipeList::class.java)
            var ingredients = txtIngredients.text.toString()
            var searchTerm = txtSearchTem.text.toString()

            intent.putExtra("ingredients", cleanString(ingredients))
            intent.putExtra("searchTerm", cleanString(searchTerm))

            startActivity(intent)
        }
    }

    fun cleanString(str: String) : String {
        return str
    }
}
