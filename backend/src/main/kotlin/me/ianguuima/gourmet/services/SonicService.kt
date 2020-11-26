package me.ianguuima.gourmet.services

import com.github.twohou.sonic.ChannelFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.lang.NullPointerException
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

        val ingest = factory.newIngestChannel()
        val control = factory.newControlChannel()

        ingest.push(collection, bucket, "dish:$id", value)

        control.consolidate()

        ingest.quit()
        control.quit()
    }

    fun remove(id : Long) {
        val factory = getChannelFactory()
        val ingest = factory.newIngestChannel()

        ingest.flusho(collection, bucket, "dish:$id")

        ingest.quit()
    }

    fun suggest(term: String): ArrayList<String> {
        val factory = getChannelFactory()
        val search = factory.newSearchChannel()

        return search.suggest(collection, bucket, term)
    }

    fun query(term: String): ArrayList<String> {
        val factory = getChannelFactory()
        val search = factory.newSearchChannel()

        try {
            return search.query(collection, bucket, term)
        } catch (ex : NullPointerException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    fun getChannelFactory() = ChannelFactory(address, port, password, connectionTimeOut, readTimeOut)



}