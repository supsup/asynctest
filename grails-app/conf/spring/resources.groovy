import com.burtbeckwith.async.AsyncLogger
import com.burtbeckwith.async.ChatManager

beans = {

    asyncLogger(AsyncLogger)

    chatManager(ChatManager) { bean ->
        bean.initMethod = 'init'
        bean.destroyMethod = 'destroy'
    }
}