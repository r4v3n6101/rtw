package rtw.server.data.impl

import rtw.common.data.RTWData
import rtw.server.data.DataRetriever

object DefaultDataRetriever : DataRetriever {
    private val emptyData by lazy { RTWData() }

    override fun retrieve() = emptyData
}