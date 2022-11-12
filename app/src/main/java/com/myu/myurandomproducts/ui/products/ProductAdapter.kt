package com.myu.myurandomproducts.ui.products

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.myu.myurandomproducts.data.entities.ProductResponseItem
import com.myu.myurandomproducts.databinding.RowProductsBinding
import com.myu.myurandomproducts.utils.Helper

class ProductAdapter(private val listener : ProductItemListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface ProductItemListener {
        fun onClickedProduct ( productId : Int)
    }

    class ProductViewHolder(private val rowProductsBinding: RowProductsBinding , private val listener: ProductItemListener) :
        RecyclerView.ViewHolder(rowProductsBinding.root) ,
        View.OnClickListener {

        private lateinit var product : ProductResponseItem
        private  var timer : CountDownTimer? = null

        init {
            rowProductsBinding.root.setOnClickListener(this)
        }

        fun bind(product : ProductResponseItem,holder: ProductViewHolder) {
            this.product = product




            holder.timer?.cancel()
            holder.timer = object : CountDownTimer(Helper().getCurrentTimeInMillis(product.remaining_time),1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val remainingTime = Helper().calculateDateGiveString(product.remaining_time)

                    rowProductsBinding.endDate.text = remainingTime
                }

                override fun onFinish() {
                }

            }.start()

            rowProductsBinding.productName.text = product.title
            rowProductsBinding.price.text = product.price.toString() + " " + "TL"
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

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(products[position],holder)



}