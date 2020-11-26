package me.ianguuima.gourmet.services

import com.github.twohou.sonic.ChannelFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.ArrayList


@Service
class SonicService {

    @Value("\${sonic.address}")
    private val address : String = ""

    private val port : Int = 1491

    @Value("\${sonic.password}")
    private val password : String = ""

    private val connectionTimeOut : Int = 5000

    private val readTimeOut : Int = 5000

    private val collection = "gourmet"

    private val bucket = "dishes"

    fun add(id: Long, value: String) {
        val factory = getChannelFactory()

        val ingestChannel = factory.newIngestChannel()
        val control = factory.newControlChannel()

        ingestChannel.push(collection, bucket, "$id", value)

        control.consolidate()
    }

    fun remove(id : Long) {
        val factory = getChannelFactory()
        val ingestChannel = factory.newIngestChannel()

        ingestChannel.flusho(collection, bucket, "$id")
    }

    fun suggest(term: String): ArrayList<String> {
        val factory = getChannelFactory()
        val search = factory.newSearchChannel()

        return search.suggest(collection, bucket, term)
    }

    fun query(term: String): ArrayList<String> {
        val factory = getChannelFactory()
        val search = factory.newSearchChannel()

        return search.query(collection, bucket, term)
    }

    fun getChannelFactory() = ChannelFactory(address, port, password, connectionTimeOut, readTimeOut)



}