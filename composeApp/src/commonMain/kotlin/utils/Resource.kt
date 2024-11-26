package utils

sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Error(val throwable: Throwable) : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
}
