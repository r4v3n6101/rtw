package rtw.client

import cpw.mods.fml.common.event.FMLInitializationEvent
import rtw.client.event.ClientEvent
import rtw.common.CommonProxy

class ClientProxy : CommonProxy() {

    override fun init(event: FMLInitializationEvent) {
        super.init(event)
        ClientEvent() // Register client event
    }
}