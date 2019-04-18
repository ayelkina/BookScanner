package com.example.bookscanner.Services

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject

//import java.net.http.HttpClient

class OCR {
    fun getOcr(): String? {
        val httpclient = HttpClients.createDefault()
        val key = "57696b14ae494d99827c2e2912b7e9a4"
        val bookAddress = "http://s.lubimyczytac.pl/upload/books/204000/204005/321402-352x500.jpg"

        try {
            var builder =
                URIBuilder("https://westeurope.api.cognitive.microsoft.com/vision/v2.0/recognizeText") //recognizeText
            builder.setParameter("mode", "Printed")

            var uri = builder.build()
            val request = HttpPost(uri)
            request.setHeader("Content-Type", "application/json")
            request.setHeader("Ocp-Apim-Subscription-Key", key)

            // Request body
            val reqEntity = StringEntity("{\"url\":\"$bookAddress\"}")
            request.setEntity(reqEntity)

            var response = httpclient.execute(request)

            val operationLocation = response.getHeaders("Operation-Location")[0]
            val urlString = operationLocation.elements[0].name

            builder = URIBuilder(urlString)
            uri = builder.build()
            val newRequest = HttpGet(uri)
            newRequest.setHeader("Ocp-Apim-Subscription-Key", key)
            var string = ""
            var i = 0
            do {
                Thread.sleep(1000);
                response = httpclient.execute(newRequest)

                val content = response.getEntity()
                string = EntityUtils.toString(content)
                ++i
            } while (i < 10 && string.indexOf("\"status\":\"Succeeded\"") == -1)

            if (i == 10 && string.indexOf("\"status\":\"Succeeded\"") == -1) {
                println("Ups")
                return null
            }

            val answer = JSONObject(string)
            val data = answer.getJSONObject("recognitionResult")
            val dataLength = data.length() - 1

            for (j in 0..dataLength) {
                val regions = data.getJSONArray("lines")
                val linesLength = regions.length() - 1
                for (k in 0..linesLength) {
                    val word = regions.getJSONObject(k)
                    val text = word["text"]
                    println(text)
                }
            }

        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }
}