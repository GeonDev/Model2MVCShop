package com.model2.mvc.vision;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.protobuf.ByteString;


//���ε�� �̹����� Ű���带 ���� �Ͽ� ����Ʈ�� ��ȯ�մϴ�.
//���� API Ű�� ���� �Ǿ� �־�� �������մϴ�. Run As - Run Configurations - Environment - New 
public class VisionKeyword { 			
		
	public VisionKeyword() {}	
	
	//�÷����� ���� ��� (R,G,B) ���·� �޾� ���� ��ü�� ����� �����ؾ� ��
	//private static final Type typeColor = Type.IMAGE_PROPERTIES;	
	
	//��� ���� ������ ����Ʈ - �ߺ����� �������� �ʱ� ���Ͽ� hashset ���� ����
	private  HashSet<String> result = new HashSet<String>();	
	
	public  List<String> getKeywordForVision(String imageFilePath) {
		
		requestVision(imageFilePath, Type.TEXT_DETECTION);
		requestVision(imageFilePath, Type.LABEL_DETECTION);
		requestVision(imageFilePath, Type.LANDMARK_DETECTION);
		requestVision(imageFilePath, Type.LOGO_DETECTION);
		
		//���� �� ��ȯ�� ���� ��ü
		return new ArrayList<String>(result);
	}
	
	private  void requestVision(String imageFilePath, Type type) {		
		try {		
			
			List<AnnotateImageRequest> requests = new ArrayList<>();
		
			ByteString imgBytes = ByteString.readFrom(new FileInputStream(imageFilePath));
		
			Image img = Image.newBuilder().setContent(imgBytes).build();
		
			Feature feat = Feature.newBuilder().setType(type).build();
			
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);
		
			try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
				
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			    List<AnnotateImageResponse> responses = response.getResponsesList();
		
			    for (AnnotateImageResponse res : responses) {
			    	if (res.hasError()) {
			    		System.out.printf("Error: %s\n", res.getError().getMessage());
			    		//���� �߻��� ����
			    		return;
			    	}			    	
			    	addKeywordList(type, res);		    	
			    }		    
		
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}	
	
	//�� Ÿ�Ը��� �޾ƾ� �ϴ� ����Ʈ ���� �ٸ� - ���⼭ ��� ó���Ѵ�.
	private void addKeywordList(Type type, AnnotateImageResponse res) {
		if(type ==  Type.TEXT_DETECTION) {
		      for (EntityAnnotation annotation : res.getTextAnnotationsList()) {		           
		           result.add(annotation.getDescription());
		        }			
		}else if(type ==  Type.LABEL_DETECTION) {
		      for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
		    	  result.add(annotation.getDescription());
		        }
		}else if(type ==  Type.LANDMARK_DETECTION) {			
			for (EntityAnnotation annotation : res.getLandmarkAnnotationsList()) {
				result.add(annotation.getDescription());
		      }
		}else if(type ==  Type.LOGO_DETECTION) {
	          for (EntityAnnotation annotation : res.getLogoAnnotationsList()) {
	        	  result.add(annotation.getDescription());
	            }
		}
		
	}
}
