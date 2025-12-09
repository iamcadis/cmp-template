package com.core.data.remote

import platform.darwin.dispatch_get_global_queue
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_status_satisfied
import kotlin.concurrent.Volatile


class IosConnectivityObserverObserver : ConnectivityObserver {
    private val monitor = nw_path_monitor_create()

    @Volatile
    private var isNetworkAvailable: Boolean = false

    init {
        // Create a background queue so network checks don't block the UI
        val queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0.toULong())

        nw_path_monitor_set_queue(monitor, queue)
        nw_path_monitor_set_update_handler(monitor) { path ->
            isNetworkAvailable = nw_path_get_status(path) == nw_path_status_satisfied
        }

        nw_path_monitor_start(monitor)
    }

    override fun isConnected(): Boolean {
        return isNetworkAvailable
    }
}