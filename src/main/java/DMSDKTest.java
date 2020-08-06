import com.marklogic.client.datamovement.DataMovementManager;
import com.marklogic.client.datamovement.JobTicket;
import com.marklogic.client.datamovement.WriteBatcher;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.StringHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DMSDKTest {
    private static final Logger logger = LoggerFactory.getLogger(DMSDKTest.class);
    private static List<String> fileContents=new ArrayList<String>();

    private static Map<String ,String> readProperties(String []args) throws Exception {
        Map<String,String> m=new HashMap<>();

        m.put("host",args[0]);
        m.put("port",args[1]);
        m.put("username",args[2]);
        m.put("password",args[3]);
        return  m;
    }

    public static void main(String[] args) throws Exception {
        Map<String,String> propertiesMap=readProperties(args);
        final MarkLogicConnection marklogic = new MarkLogicConnection(propertiesMap.get("host"),propertiesMap.get("port"),
                propertiesMap.get("username"),propertiesMap.get("password"));

         DataMovementManager manager = marklogic.getClient().newDataMovementManager();
        WriteBatcher batcher = manager.newWriteBatcher().withBatchSize(100).withThreadCount(10).onBatchSuccess(batch -> {
            logger.info(batch.getTimestamp().getTime() +" documents written: "+ batch.getJobWritesSoFar());
        }).onBatchFailure((b, throwable) -> {
            throwable.printStackTrace();
        });
        try {
            fileContents=Files.walk(Paths.get("../data"))
                    .filter(Files::isRegularFile)
                    .map(p->{
                        Stream<String> lines = null;
                        try {
                            lines = Files.lines(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String data = lines.collect(Collectors.joining("\n"));
                        lines.close();
                        return data.trim();

                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JobTicket ticket = manager.startJob(batcher);
        for(String event : fileContents){
            final String uri = "/events-v2-archive/"+ UUID.randomUUID().toString()+".json";
            StringHandle stringHandle = new StringHandle().withFormat(Format.JSON).with(event);
            DocumentMetadataHandle dmHandle = new DocumentMetadataHandle();
            dmHandle.getPermissions().add("rest-writer", DocumentMetadataHandle.Capability.READ, DocumentMetadataHandle.Capability.UPDATE);
            dmHandle.getCollections().add("events-v2-archive");
            batcher.add(uri, dmHandle, stringHandle);
        }

        batcher.flushAndWait();

        manager.stopJob(batcher);

    }
}
