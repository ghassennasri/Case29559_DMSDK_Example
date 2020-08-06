# Case29559_DMSDK_Example
## Prequisites :
* MarkLogic server 9+ instance is necessary
* JAVA_HOME environment variable pointing to java 8
## Configuration :
* Change mlHost to point to a MarkLogic server e-node
* You can also change other properties in gradle.properties as needed
## Execution
__runDMSDKTestWithURIPrivGrantedUser__ and __runDMSDKTestWithURIPrivDeniedUser__
gradle custom tasks depend on mlDeploy executing any of them will ensure deploying REST server (port 8013),
URI privilege events-v2-archive, roles (uri-priv-granted-role and uri-priv-denied-role) and users (uri-priv-denied-user and uri-priv-granted-user)
 to the MarkLogic.

Executing __runDMSDKTestWithURIPrivDeniedUser__ task, runs the DMSDK batch with the user "uri-priv-denied-user" having a role "uri-priv-denied-role"
                                                      
```json                                                      
{
    "role-name":"uri-priv-denied-role",
    "description":"",
    "role":["rest-reader", "rest-admin", "rest-writer"]
}
``` 
the output is as follows ;

    [main] INFO MarkLogicConnection - Creating Connection to MarkLogic db on ml10.eng.marklogic.com:8013
    [main] DEBUG OkHttpServices - Connecting to ml10.eng.marklogic.com at 8013 as uri-priv-denied-user
    [main] DEBUG OkHttpServices - Getting internal/forestinfo as application/json
    [main] INFO WriteBatcherImpl - (withForestConfig) Using forests on [ml10-2.eng.marklogic.com, ml10-1.eng.marklogic.com, ml10.eng.marklogic.com] hosts for "Case29559-content"
    [main] DEBUG OkHttpServices - Connecting to ml10-2.eng.marklogic.com at 8013 as uri-priv-denied-user
    [main] INFO WriteBatcherImpl - Adding DatabaseClient on port 8013 for host "ml10-2.eng.marklogic.com" to the rotation
    [main] DEBUG OkHttpServices - Connecting to ml10-1.eng.marklogic.com at 8013 as uri-priv-denied-user
    [main] INFO WriteBatcherImpl - Adding DatabaseClient on port 8013 for host "ml10-1.eng.marklogic.com" to the rotation
    [main] INFO WriteBatcherImpl - Adding DatabaseClient on port 8013 for host "ml10.eng.marklogic.com" to the rotation
    [main] INFO WriteBatcherImpl - threadCount=10
    [main] INFO WriteBatcherImpl - batchSize=100
    [main] INFO WriteBatcherImpl - flushing 6 queued docs
    [pool-1-thread-1] DEBUG OkHttpServices - Posting documents
    [pool-1-thread-1] DEBUG OkHttpServices - Sending multipart for /v1/documents
    com.marklogic.client.FailedRequestException: Local message: failed to apply resource at documents: Bad Request. Server Message: SEC-URIPRIV: xdmp:document-insert("/events-v2-archive/15e3ed00-45db-4e7f-bd1f-16f619315ce3.json", document{object-node{"conformsTo":text{"https://project-open-data.cio.gov/..."}, ...}}, <options xmlns="xdmp:document-insert"><collections><collection>events...</collection></collections>...</options>) -- URI privilege required
	    at com.marklogic.client.impl.OkHttpServices.checkStatus(OkHttpServices.java:4290)
	    at com.marklogic.client.impl.OkHttpServices.postResource(OkHttpServices.java:3362)
	    at com.marklogic.client.impl.OkHttpServices.postBulkDocuments(OkHttpServices.java:3442)
	    at com.marklogic.client.impl.DocumentManagerImpl.write(DocumentManagerImpl.java:614)
	    at com.marklogic.client.impl.GenericDocumentImpl.write(GenericDocumentImpl.java:23)
	    at com.marklogic.client.impl.DocumentManagerImpl.write(DocumentManagerImpl.java:606)
	    at com.marklogic.client.impl.GenericDocumentImpl.write(GenericDocumentImpl.java:23)
	    at com.marklogic.client.datamovement.impl.WriteBatcherImpl$BatchWriter.run(WriteBatcherImpl.java:1076)
	    at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	    at java.lang.Thread.run(Thread.java:748)
    [pool-1-thread-1] WARN WriteBatcherImpl - Error writing batch: com.marklogic.client.FailedRequestException: Local message: failed to apply resource at documents: Bad Request. Server Message: SEC-URIPRIV: xdmp:document-insert("/events-v2-archive/15e3ed00-45db-4e7f-bd1f-16f619315ce3.json", document{object-node{"conformsTo":text{"https://project-open-data.cio.gov/..."}, ...}}, <options xmlns="xdmp:document-insert"><collections><collection>events...</collection></collections>...</options>) -- URI privilege required 
 
Executing __runDMSDKTestWithURIPrivGrantedUser__ task, runs with the user "uri-priv-granted-user" having a role "uri-priv-granted-role" 
                                                      
```json                                                      
{
"role-name":"uri-priv-granted-role",
"description":"",
"role":["rest-reader", "rest-admin", "rest-writer"],
"privilege":[{"privilege-name":"events-v2-archive", "action":"/events-v2-archive/", "kind":"uri"}]
}
``` 
the output is as follows ;

    [main] INFO MarkLogicConnection - Creating Connection to MarkLogic db on ml10.eng.marklogic.com:8013
    [main] DEBUG OkHttpServices - Connecting to ml10.eng.marklogic.com at 8013 as uri-priv-granted-user
    [main] DEBUG OkHttpServices - Getting internal/forestinfo as application/json
    [main] INFO WriteBatcherImpl - (withForestConfig) Using forests on [ml10-2.eng.marklogic.com, ml10-1.eng.marklogic.com, ml10.eng.marklogic.com] hosts for "Case29559-content"
    [main] DEBUG OkHttpServices - Connecting to ml10-2.eng.marklogic.com at 8013 as uri-priv-granted-user
    [main] INFO WriteBatcherImpl - Adding DatabaseClient on port 8013 for host "ml10-2.eng.marklogic.com" to the rotation
    [main] DEBUG OkHttpServices - Connecting to ml10-1.eng.marklogic.com at 8013 as uri-priv-granted-user
    [main] INFO WriteBatcherImpl - Adding DatabaseClient on port 8013 for host "ml10-1.eng.marklogic.com" to the rotation
    [main] INFO WriteBatcherImpl - Adding DatabaseClient on port 8013 for host "ml10.eng.marklogic.com" to the rotation
    [main] INFO WriteBatcherImpl - threadCount=10
    [main] INFO WriteBatcherImpl - batchSize=100
    [main] INFO WriteBatcherImpl - flushing 6 queued docs
    [pool-1-thread-1] DEBUG OkHttpServices - Posting documents
    [pool-1-thread-1] DEBUG OkHttpServices - Sending multipart for /v1/documents
    [pool-1-thread-1] INFO DMSDKTest - Thu Aug 06 13:33:23 CEST 2020 documents written: 6