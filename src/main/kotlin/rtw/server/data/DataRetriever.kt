package rtw.server.data

import rtw.common.data.RTWData

interface DataRetriever {

    fun retrieve(): RTWData
}