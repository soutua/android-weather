package org.roun.weather.dagger

import dagger.Module
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import android.app.Application
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Provides
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.roun.weather.BuildConfig
import org.roun.weather.moshi.DateJsonAdapter
import org.roun.weather.network.WeatherAPI
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        val cache = Cache(application.cacheDir, cacheSize)
        return cache
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        client.addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url()
            val url = originalUrl.newBuilder()
                    .addQueryParameter("APPID", BuildConfig.API_KEY)
                    .build()
            val requestBuilder = originalRequest.newBuilder()
                    .url(url)
            val request = requestBuilder.build()
            chain.proceed(request) }

        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .add(DateJsonAdapter()).build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideWeatherAPI(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }
}
