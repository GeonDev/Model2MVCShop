package com.model2.mvc.service;

import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class VisionTest {
	
	public static void main(String[] args) {
		
		try {
	
			String imageFilePath = "C:\\workspace\\Model2MVCShop\\src\\test\\java\\com\\model2\\mvc\\service\\logo-spring.png"; //여기 설정해줘야함(test이미지 경로)
			
			List<AnnotateImageRequest> requests = new ArrayList<>();
		
			ByteString imgBytes = ByteString.readFrom(new FileInputStream(imageFilePath));
		
			Image img = Image.newBuilder().setContent(imgBytes).build();
			//Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
			Feature feat = Feature.newBuilder().setType(Type.IMAGE_PROPERTIES).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);
		
			try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
				
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			    List<AnnotateImageResponse> responses = response.getResponsesList();
		
			    for (AnnotateImageResponse res : responses) {
			    	if (res.hasError()) {
			    		System.out.printf("Error: %s\n", res.getError().getMessage());
			    		return;
			    	}
		
			    	System.out.println("Text : ");
			    	//System.out.println(res.getTextAnnotationsList().get(0).getDescription());			      	
			    	System.out.println(res);
			    	
			    }
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
