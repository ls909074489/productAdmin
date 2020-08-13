package com.yy.common.utils.oss.sdk.sample;

import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.SetBucketCORSRequest;
import com.aliyun.oss.model.SetBucketCORSRequest.CORSRule;

public class LsDemo {
	

    
	private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
  private static String accessKeyId = "your accessKeyId";
  private static String accessKeySecret = "your accessKeySecret";
  private static String bucketName = "bucket-csc";
    
    private static OSSClient client = null;

  
    
    
	public static void main(String[] args) {
		 client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		  
		  
		doBucketCORSOperations();
	}
	
	 private static void doBucketCORSOperations() {
	        SetBucketCORSRequest request = new SetBucketCORSRequest(bucketName);
	        
	        CORSRule r0 = new CORSRule();
	        r0.addAllowdOrigin("http://172.16.16.72:8080*");
	        r0.addAllowdOrigin("http://172.16.16.75*");
	        r0.addAllowdOrigin("http://csc.meizu.com*");
	        r0.addAllowdOrigin("http://tcsc.meizu.com*");
	        r0.addAllowdOrigin("http://localhost:8080*");
	        r0.addAllowedMethod("POST");
	        r0.addAllowedHeader("*");// r0.addAllowedHeader("Authorization");
//	        r0.addExposeHeader("x-oss-test");
//	        r0.addExposeHeader("x-oss-test1");
//	        r0.setMaxAgeSeconds(100);
	        request.addCorsRule(r0);
	        
	        System.out.println("Setting bucket CORS\n");
	        client.setBucketCORS(request);
	        
	        System.out.println("Getting bucket CORS:");
	        List<CORSRule> rules = client.getBucketCORSRules(bucketName);
	        for(int i=0;i<rules.size();i++){
		        r0 = rules.get(i);
		        System.out.println("\tAllowedOrigins " + r0.getAllowedOrigins());
		        System.out.println("\tAllowedMethods " + r0.getAllowedMethods());
		        System.out.println("\tAllowedHeaders " + r0.getAllowedHeaders());
		        System.out.println("\tExposeHeaders " + r0.getExposeHeaders());
		        System.out.println("\tMaxAgeSeconds " + r0.getMaxAgeSeconds());
		        System.out.println();
	        }

	        
//	        System.out.println("Deleting bucket CORS\n");
//	        client.deleteBucketCORSRules(bucketName);
	    }
}
