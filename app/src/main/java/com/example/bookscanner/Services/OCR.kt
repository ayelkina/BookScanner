package com.example.bookscanner.Services

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject
import org.apache.http.entity.FileEntity
import java.io.File


class OCR {
    private val httpclient = HttpClients.createDefault()
    private val key = "5c22670cbe4f49b888973bf504f93ca8"
    private val apiURL = "https://westeurope.api.cognitive.microsoft.com/vision/v2.0/recognizeText"

    fun getOcr(file: File): List<String>? = runBlocking{

        val urlString  = GlobalScope.async { getClientUrlFromApi(file)}
        val jsonString = GlobalScope.async { makeRequest(urlString.await())
        }

        getOcrText(jsonString.await())
    }

    private fun getClientUrlFromApi(file: File): String {
        val builder =
            URIBuilder(apiURL) //recognizeText
        builder.setParameter("mode", "Handwritten")

        val uri = builder.build()
        val request = HttpPost(uri)
        request.setHeader("Ocp-Apim-Subscription-Key", key)
        request.setEntity(FileEntity(file, "application/octet-stream"))

        val response = httpclient.execute(request)
        val operationLocation = response.getHeaders("Operation-Location")[0]
        val urlString = operationLocation.elements[0].name
        return urlString
    }

    private fun makeRequest(urlString: String): String {
        if(urlString == "") {
            return ""
        }
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