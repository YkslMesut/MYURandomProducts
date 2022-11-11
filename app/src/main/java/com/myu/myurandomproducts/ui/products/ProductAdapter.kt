package com.myu.myurandomproducts.ui.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.myu.myurandomproducts.data.entities.ProductResponseItem
import com.myu.myurandomproducts.databinding.RowProductsBinding

class ProductAdapter(private val listener : ProductItemListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface ProductItemListener {
        fun onClickedProduct ( productId : Int)
    }

    class ProductViewHolder(private val rowProductsBinding: RowProductsBinding , private val listener: ProductItemListener) :
        RecyclerView.ViewHolder(rowProductsBinding.root) ,
        View.OnClickListener {

        private lateinit var product : ProductResponseItem

        init {
            rowProductsBinding.root.setOnClickListener(this)
        }

        fun bind(product : ProductResponseItem) {
            this.product = product

            rowProductsBinding.productName.text = product.title
            rowProductsBinding.price.text = product.price.toString() + " " + "TL"
            rowProductsBinding.endDate.text = "25 Kasım 2022"
            Glide.with(rowProductsBinding.root)
               .load(product.image)
                .transform(CircleCrop())
                .into(rowProductsBinding.productImage)
        }

        override fun onClick(v: View?) {
            listener.onClickedProduct(product.id)
        }
    }

    private val products = ArrayList<ProductResponseItem>()

    fun setProducts(items : ArrayList<ProductResponseItem>) {
        this.products.clear()
        this.products.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding : RowProductsBinding = RowProductsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding,listener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(products[position])



}