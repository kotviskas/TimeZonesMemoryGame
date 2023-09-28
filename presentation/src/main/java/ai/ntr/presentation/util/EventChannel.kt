package ai.ntr.presentation.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EventChannel<T> {
    val channel = Channel<T>(Channel.BUFFERED)
    fun send(value: T, scope: CoroutineScope) = scope.launch { channel.send(value) }
    fun flow() = channel.receiveAsFlow()
    suspend fun send(value: T) = channel.send(value)
}

@Suppress("FunctionName")
fun EventChannel(): EventChannel<Unit> = EventChannel<Unit>()

suspend fun EventChannel<Unit>.send() = send(Unit)
fun EventChannel<Unit>.send(scope: CoroutineScope) = scope.launch { channel.send(Unit) }

public fun <T> Flow<T>.flowWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = callbackFlow {
    lifecycle.repeatOnLifecycle(minActiveState) {
        this@flowWithLifecycle.collect {
            send(it)
        }
    }
    close()
}
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { action(it) }
}