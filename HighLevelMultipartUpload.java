
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import java.io.File;

import java.io.File;

public class HighLevelMultipartUpload {

    public static void main(String[] args) throws Exception {
        
    	
    	Regions clientRegion = Regions.AP_SOUTH_1; /* Region*/

        String bucketName = "sawanashram"; /* Bucket Name*/
        String keyName;
        
        String dir = "/Users/arihantjain/source"; /*Source dir to upload files*/
        String filePath;
        int i =0;
        File folder = new File(dir);
        
        System.out.println(folder);
        
        for (final File fileEntry : folder.listFiles()) {
                keyName = fileEntry.getName();
                
                filePath = dir+"/"+keyName;
                System.out.println(filePath);
                try {
                    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                            .withRegion(clientRegion)
                            .withCredentials(new ProfileCredentialsProvider())
                            .build();
                    TransferManager tm = TransferManagerBuilder.standard()
                            .withS3Client(s3Client)
                            .build();

                    // TransferManager processes all transfers asynchronously,
                    // so this call returns immediately.
                    Upload upload = tm.upload(bucketName, keyName, new File(filePath));
                    System.out.println("Object upload started");

                    // Optionally, wait for the upload to finish before continuing.
                    upload.waitForCompletion();
                    System.out.println("Object upload complete");
                    i++;
                }
              
                catch (AmazonServiceException e) {
                    // The call was transmitted successfully, but Amazon S3 couldn't process 
                    // it, so it returned an error response.
                    e.printStackTrace();
                } catch (SdkClientException e) {
                    // Amazon S3 couldn't be contacted for a response, or the client
                    // couldn't parse the response from Amazon S3.
                    e.printStackTrace();
                }
         System.out.println(i+" files uploaded");
            }
   
    
    }
}

