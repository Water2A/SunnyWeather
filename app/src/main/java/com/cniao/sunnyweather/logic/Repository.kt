package com.cniao.sunnyweather.logic

import androidx.lifecycle.liveData
import com.cniao.sunnyweather.logic.model.Place
import com.cniao.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {

    /**
     * 代码中的liveData()函数是lifecycle-livedata-ktx库提供的一个功能，它可以自动构建并返回一个LiveData对象，
     * 然后在它的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数的代码块中调用任意的挂起函数了。
     * 这里调用了SunnyWeatherNetwork的searchPlaces()函数来搜索城市数据，如果服务器返回的状态是ok，那么就使用
     * Kotlin内置的Result.success()方法来包装获取的城市数据列表，否则使用Result.failure()方法来包装一个异常
     * 信息。最后使用一个emit方法将包装的数据包装出去，这个emit()方法类似于调用LiveData的setValue方法来通知数据
     * 变化，只不过这里我们无法直接取得返回的LiveData对象，所以lifecycle-livedata-ktx库提供了这样一个替代方法。
     *
     * 代码中我们还将liveData()函数的线程参数指定成了Dispatchers.IO，这样代码块中的所有代码都运行在子线程中了
     */
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}