package ai.ntr.domain.base

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCaseNoParams<T> : BaseUseCase<T,Unit>() {

    override fun doUseCase(params: Unit): T = doUseCase()

    operator fun invoke(): Result<T> = invoke(Unit)

    protected abstract fun doUseCase(): T
}