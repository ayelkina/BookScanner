package com.example.bookscanner.Services

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject


class OCR {
    private val httpclient = HttpClients.createDefault()
    private val key = "57696b14ae494d99827c2e2912b7e9a4"
    private val apiURL = "https://westeurope.api.cognitive.microsoft.com/vision/v2.0/recognizeText"

    fun getOcr(imageBitmap: ByteArray): List<String>? = runBlocking{

        val urlString  = GlobalScope.async { getClientUrlFromApi(imageBitmap)}
        val jsonString = GlobalScope.async { makeRequest(urlString.await())
        }

        getOcrText(jsonString.await())
    }



    private fun getClientUrlFromApi(imageBitmap: ByteArray): String {
        val bookAddress = "http://s.lubimyczytac.pl/upload/books/204000/204005/321402-352x500.jpg" //mock
        val builder =
            URIBuilder(apiURL) //recognizeText
        builder.setParameter("mode", "Printed")

        val uri = builder.build()
        val request = HttpPost(uri)
        request.setHeader("Content-Type", "application/json")
//        request.setHeader("Content-Type", "application/octet-stream")
        request.setHeader("Ocp-Apim-Subscription-Key", key)

        val reqEntity = StringEntity("{\"url\":\"$bookAddress\"}")
//        val reqEntity = StringEntity("[" + imageBitmap + "]")
        request.setEntity(reqEntity)

        val response = httpclient.execute(request)
        val operationLocation = response.getHeaders("Operation-Location")[0]
        val urlString = operationLocation.elements[0].name

        return urlString
    }

    private fun makeRequest(urlString: String): String {
        val builder = URIBuilder(urlString)
        val uri = builder.build()
        val newRequest = HttpGet(uri)
        newRequest.setHeader("Ocp-Apim-Subscription-Key", key)
        var jsonString = ""
        var i = 0
        do {
            Thread.sleep(1000);
            val response = httpclient.execute(newRequest)

            val content = response.getEntity()
            jsonString = EntityUtils.toString(content)
            ++i
        } while (i < 10 && jsonString.indexOf("\"status\":\"Succeeded\"") == -1)

        return jsonString
    }

    private fun getOcrText(jsonString: String): MutableList<String> {
        val ocrText = mutableListOf<String>()
        val answer = JSONObject(jsonString)
        val data = answer.getJSONObject("recognitionResult")
        val dataLength = data.length() - 1

        for (j in 0..dataLength) {
            val regions = data.getJSONArray("lines")
            val linesLength = regions.length() - 1
            for (k in 0..linesLength) {
                val word = regions.getJSONObject(k)
                val text = word["text"].toString()
                ocrText.add(text)
            }
        }

        return ocrText
    }
}