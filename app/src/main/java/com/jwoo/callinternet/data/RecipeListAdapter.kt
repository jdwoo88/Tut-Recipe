package com.jwoo.callinternet.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jwoo.callinternet.R
import com.jwoo.callinternet.models.Recipe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_row.view.*


class RecipeListAdapter(private val list: ArrayList<Recipe>,
                       private val context: Context) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.imgRecipeList
        var title = itemView.txtRecipeTitleList
        var ingredients = itemView.txtIngredientsList
        var shareLink = itemView.btnRecipeLinkList

        fun  bindView(recipe: Recipe) {
            title.text = recipe.Title
            ingredients.text = recipe.Ingredients
            shareLink.setOnClickListener {
                val uris = Uri.parse(recipe.Link)
                val intents = Intent(Intent.ACTION_VIEW, uris)
                val b = Bundle()
                b.putBoolean("new_window", true)
                intents.putExtras(b)
                context.startActivity(intents)
            }
            if (!TextUtils.isEmpty(recipe.Thumbnail)){
                Picasso.with(context)
                    .load(recipe.Thumbnail)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(img as ImageView)
            }
            else{
                Picasso.with(context)
                    .load(R.mipmap.ic_launcher)
                    .into(img as ImageView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecipeListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}