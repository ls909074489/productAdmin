package com.yy.common.utils.oss.sdk.sample;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/**
 * This sample demonstrates how to list objects under specfied bucket 
 * from Aliyun OSS using the OSS SDK for Java.
 */
public class ListObjectsSample {
	//mz
//	private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//    private static String accessKeyId = "xmYt1ZCeJyA8yWkL";
//    private static String accessKeySecret = "z4UzrXOIqvDSiagXJeMcwefxtaq6fy";
//    private static String bucketName = "bucket-csc";
    //anshun
//	  private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
//	  private static String accessKeyId = "Yf3zvuMZLIJ38RP4";
//	  private static String accessKeySecret = "K5yzm9zec3qYe8A2x3qQnzjlyQEShr";
//	  private static String bucketName = "mzcsc";
    
     //ls2008
	  private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
	  private static String accessKeyId = "5uLVJDKTjUez1VsD";
	  private static String accessKeySecret = "AZ20Sv025NRHuW3tWcWQRKfM3DJKcT";
	  private static String bucketName = "lsbucket";
    
    public static void main(String[] args) throws IOException {        
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        System.out.println("start================================================"+endpoint);
        try {
            /*
             * Batch put objects into the bucket
             */
            final String content = "Thank you for using Aliyun Object Storage Service";
            final String keyPrefix = "MyObjectKey";
            List<String> keys = new ArrayList<String>();
            for (int i = 0; i < 5; i++) {
                String key = keyPrefix + i;
                InputStream instream = new ByteArrayInputStream(content.getBytes());
                client.putObject(bucketName, key, instream);
                System.out.println("Succeed to put object " + key);
                keys.add(key);
            }
            System.out.println();
            
            /*
             * List objects under the bucket 
             */
            ObjectListing objectListing = null;
            String nextMarker = null;
            final int maxKeys = 30;
            
            do {
                System.out.println("Listing objects:");
                objectListing = client.listObjects(new ListObjectsRequest(bucketName).
                        withMarker(nextMarker).withMaxKeys(maxKeys));
                
                List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary s : sums) {
                    System.out.println("\t" + s.getKey());
                }
                
                nextMarker = objectListing.getNextMarker();
            } while (objectListing.isTruncated());
            
            /*
             * Delete all objects uploaded recently under the bucket
             */
            System.out.println("\nDeleting all objects:");
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(
                    new DeleteObjectsRequest(bucketName).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            for (String object : deletedObjects) {
                System.out.println("\t" + object);
            }
            System.out.println();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
}
