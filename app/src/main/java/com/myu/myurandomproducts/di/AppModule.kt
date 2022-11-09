package com.myu.myurandomproducts.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myu.myurandomproducts.data.local.AppDatabase
import com.myu.myurandomproducts.data.local.ProductDao
import com.myu.myurandomproducts.data.remote.Constants
import com.myu.myurandomproducts.data.remote.ProductRemoteDataSource
import com.myu.myurandomproducts.data.remote.ProductService
import com.myu.myurandomproducts.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(productService: ProductService) = ProductRemoteDataSource(productService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideProductDao(db: AppDatabase) = db.productDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ProductRemoteDataSource,
                          localDataSource: ProductDao) =
        ProductRepository(remoteDataSource, localDataSource)
}