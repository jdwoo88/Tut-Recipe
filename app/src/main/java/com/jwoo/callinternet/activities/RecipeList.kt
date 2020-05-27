package com.jwoo.callinternet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jwoo.callinternet.R
import com.jwoo.callinternet.data.RecipeListAdapter
import com.jwoo.callinternet.models.BASE_LINK
import com.jwoo.callinternet.models.INGREDIENTS_STRING
import com.jwoo.callinternet.models.Recipe
import com.jwoo.callinternet.models.SEARCH_STRING
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null
    var recipeListAdapter: RecipeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        volleyRequest = Volley.newRequestQueue(this)
        recipeList = ArrayList<Recipe>()

        var finalUrl: String? = null

        var extras = intent.extras
        var ingredients = extras?.getString("ingredients")
        var searchTerm = extras?.getString("searchTerm")

        if (extras != null && !TextUtils.isEmpty(ingredients)  && !TextUtils.isEmpty(searchTerm)){
            finalUrl = BASE_LINK + INGREDIENTS_STRING + ingredients + "&" + SEARCH_STRING + searchTerm
        }
        else if(extras != null && !TextUtils.isEmpty(ingredients)){
            finalUrl = BASE_LINK + INGREDIENTS_STRING + ingredients
        }
        else if(extras != null && !TextUtils.isEmpty(searchTerm)){
            finalUrl = BASE_LINK + SEARCH_STRING + searchTerm
        }
        else{
            finalUrl = BASE_LINK
        }

        getRecipe(finalUrl)


    }

    fun getRecipe(url: String){
        val recipeRequest = JsonObjectRequest(
            Request.Method.GET, url,
            Response.Listener {
                    response: JSONObject ->
                try {
                    val resultArray = response.getJSONArray("results")
                    if (resultArray.length() > 0){
                        for(i in 0..resultArray.length() - 1){
                            var recipeObj = resultArray.getJSONObject(i)

                            var title = cleanString(recipeObj.getString("title"))
                            var link = recipeObj.getString("href")
                            var ingredients = recipeObj.getString("ingredients")
                            var thumbnail = replaceEmptyThumbnail(recipeObj.getString("thumbnail"))

                            recipeList!!.add(Recipe(title, link, ingredients, thumbnail))
                        }

                        recipeListAdapter = RecipeListAdapter(recipeList!!, this)
                        layoutManager = LinearLayoutManager(this)

                        recyclerViewRecipe.layoutManager = layoutManager
                        recyclerViewRecipe.adapter = recipeListAdapter

                        recipeListAdapter!!.notifyDataSetChanged()
                    }
                }
                catch(e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                    error: VolleyError? ->
                try {
                    Log.d("RecipeFinder", error.toString())
                }
                catch(e: JSONException){
                    e.printStackTrace()
                }
            })

        volleyRequest!!.add(recipeRequest)
    }

    fun cleanString(str: String?) :String {
        var result:String = ""

        if (!TextUtils.isEmpty(str)) {
            result = str!!.replace("\n", "")
            result = result.replace("\r\n", "")
            result = result.replace("\n\n", "")
        }
        else {
            result = ""
        }

        return result
    }

    fun replaceEmptyThumbnail(str: String?) : String {
        var result:String = ""

        if (!TextUtils.isEmpty(str)) {
            result = str.toString()
        }
        else {
            result = "http://img.recipepuppy.com/751345.jpg"
        }

        return result
    }
}
