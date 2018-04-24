package org.rundeck.api.services

import com.mongodb.MongoClient
import groovy.json.JsonOutput
import net.sf.json.JSONSerializer

import java.text.SimpleDateFormat

import groovyx.net.http.*
import org.bson.Document


class RundeckService {

    def auth = ""
    def endpointUrl = ""
    def endpointBase = ""

    def MONGO_HOST = "mongo"
    def MONGO_PORT  = 27017

    /*private getProperties() {
        Properties properties = new Properties()
        File propertiesFile = new File('rundeck-config.txt')

        propertiesFile.withInputStream {
            properties.load(it)
        }

        return properties
    }*/

    def getData(String url){
        try {
            def props = new Properties()
            props = getProperties()

            /*def auth = props.auth
            def endpointUrl = props.endpointUrl
            def endpointBase = props.endpointBase*/

            print "+++++++ " + endpointUrl + " +++++++ "
            print "+++++++ " + endpointUrl + " +++++++ "

            def client = new RESTClient(endpointUrl)
            def emptyHeaders = [:]
            emptyHeaders."Accept" = 'application/json'
            emptyHeaders."X-Rundeck-Auth-Token" = auth
            client.ignoreSSLIssues()
            def path = endpointBase+url
            def response = client.get(path: path, headers: emptyHeaders)
            if (response) {
                response = JsonOutput.toJson(response.data)

                //saveJsonArray(response)
                //saveSimpleDocument() // funciona, guarda un simple Document
                saveJsonObject(response)

                return response
            }
        }catch(response) {
            return "Unexpected response error: ${response}"
        }
    }


    def getData_JsonObject(String url){
        try {
            def props = new Properties()
            props = getProperties()

            /*def auth = props.auth
            def endpointUrl = props.endpointUrl
            def endpointBase = props.endpointBase*/

            print "+++++++ " + endpointUrl + " +++++++ "

            def client = new RESTClient(endpointUrl)
            def emptyHeaders = [:]
            emptyHeaders."Accept" = 'application/json'
            emptyHeaders."X-Rundeck-Auth-Token" = auth
            client.ignoreSSLIssues()
            def path = endpointBase+url
            def response = client.get(path: path, headers: emptyHeaders)
            if (response) {
                response = JsonOutput.toJson(response.data)
                saveJsonObject(response)
                return response
            }
        }catch(response) {
            return "Unexpected response error: ${response}"
        }
    }

    def getData_JsonArray(String url){
        try {
            def props = new Properties()
            props = getProperties()

            /*def auth = props.auth
            def endpointUrl = props.endpointUrl
            def endpointBase = props.endpointBase*/

            print "+++++++ " + endpointUrl + " +++++++ "

            def client = new RESTClient(endpointUrl)
            def emptyHeaders = [:]
            emptyHeaders."Accept" = 'application/json'
            emptyHeaders."X-Rundeck-Auth-Token" = auth
            client.ignoreSSLIssues()
            def path = endpointBase+url
            def response = client.get(path: path, headers: emptyHeaders)
            if (response) {
                response = JsonOutput.toJson(response.data)
                saveJsonArray(response)
                return response
            }
        }catch(response) {
            return "Unexpected response error: ${response}"
        }
    }


    //Funciona para GET projects, GET jobs
    def saveJsonArray(String arreglo){
        List<Document> jsonList = new ArrayList<Document>()
        net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(arreglo)
        for (Object object : array) {
            net.sf.json.JSONObject jsonStr = (net.sf.json.JSONObject) JSONSerializer.toJSON(object)
            Document jsnObject = Document.parse(jsonStr.toString())
            jsonList.add(jsnObject)

        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())
        new MongoClient(MONGO_HOST, MONGO_PORT).getDatabase("rundeckdb").getCollection("rdkcollection-"+timeStamp).insertMany(jsonList)
    }

    // Funciona para JsonObjects, jobs running, version
    def saveJsonObject(String json){
        Document doc = Document.parse(json)
        List<Document> list = new ArrayList<>()
        list.add(doc)

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())
        new MongoClient(MONGO_HOST, MONGO_PORT).getDatabase("rundeckdb").getCollection("rdkcollection-"+timeStamp).insertMany(list)
    }

    //solo para pruebas
    /*def saveSimpleDocument(){
        //MongoClient mongo = new MongoClient("127.0.0.1",27017)
        MongoClient mongo = new MongoClient("192.168.16.2",27017)
        MongoDatabase database = mongo.getDatabase("rundeck")
        MongoCollection<Document> mongoCollection = database.createCollection("rdkcollection")
        MongoCollection<Document> collection = database.getCollection("rdkcollection")

        Document document = new Document("name", "Café Con Leche")
                .append("contact", new Document("phone", "228-555-0149")
                .append("email", "cafeconleche@example.com")
                .append("location",Arrays.asList(-73.92502, 40.8279556)))
                .append("stars", 3)
                .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"))
        collection.insertOne(document)
    }*/

    def postData(String url){
        try {
            def props = new Properties()
            props = getProperties()
            def auth = props.auth
            def endpointUrl = props.endpointUrl
            def endpointBase = props.endpointBase
            def client = new RESTClient(endpointUrl)
            def emptyHeaders = [:]
            emptyHeaders."Accept" = 'application/json'
            emptyHeaders."X-Rundeck-Auth-Token" = auth
            client.ignoreSSLIssues()
            def path = endpointBase+url
            def response = client.post(path: path, headers: emptyHeaders)
            if (response.data) {

                response = JsonOutput.toJson(response.data)
                return response
            }
        }catch(HttpResponseException ex) {
            return "Unexpected response error: ${ex.response}"
        }
    }
}
