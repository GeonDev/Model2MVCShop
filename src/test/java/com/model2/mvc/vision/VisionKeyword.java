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


//업로드된 이미지의 키워드를 추출 하여 리스트로 반환합니다.
//구글 API 키가 세팅 되어 있어야 정상동작합니다. Run As - Run Configurations - Environment - New 
public class VisionKeyword { 			
		
	public VisionKeyword() {}	
	
	//컬러값을 차후 고려 (R,G,B) 형태로 받아 따로 객체를 만들어 전달해야 함
	//private static final Type typeColor = Type.IMAGE_PROPERTIES;	
	
	//결과 값을 저장할 리스트 - 중복값을 저장하지 않기 위하여 hashset 으로 지정
	private  HashSet<String> result = new HashSet<String>();	
	
	public  List<String> getKeywordForVision(String imageFilePath) {
		
		requestVision(imageFilePath, Type.TEXT_DETECTION);
		requestVision(imageFilePath, Type.LABEL_DETECTION);
		requestVision(imageFilePath, Type.LANDMARK_DETECTION);
		requestVision(imageFilePath, Type.LOGO_DETECTION);
		
		//최종 값 반환을 위한 객체
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
			    		//오류 발생시 정지
			    		return;
			    	}			    	
			    	addKeywordList(type, res);		    	
			    }		    
		
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}	
	
	//각 타입마다 받아야 하는 리스트 값이 다름 - 여기서 묶어서 처리한다.
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
