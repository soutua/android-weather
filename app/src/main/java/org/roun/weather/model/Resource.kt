package org.roun.weather.model

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }
    }
}
