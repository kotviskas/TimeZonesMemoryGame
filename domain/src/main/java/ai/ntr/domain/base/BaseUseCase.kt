package ai.ntr.domain.base

abstract class BaseUseCase<T, in Params> {

    operator fun invoke(params: Params): Result<T> {
        return try {
            val result = doUseCase(params)
            Result.success(result)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    protected abstract fun doUseCase(params: Params): T
}