package com.dev.frontend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dev.frontend.panels.ComboBoxItem;
import com.dev.frontend.panels.Main;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Services
{
	public static final int TYPE_PRODUCT = 1;
	public static final int TYPE_CUSTOMER = 2;
	public static final int TYPE_SALESORDER = 3;


	public static Object save(Object object,int objectType)
	{
        if (object != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.postForEntity(
                    getObjectTypeUrl(objectType) + "/save",
                    object,
                    String.class
            );
            ObjectMapper mapper = new ObjectMapper();

            try {
                return mapper.readValue(result.getBody(), new TypeReference<Map<String, Object>>() {
                });
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
		return null;
	}
	public static Object readRecordByCode(String code,int objectType)
	{
        if (code != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.getForEntity(
                    getObjectTypeUrl(objectType) + "/" + code + "/get",
                    String.class
            );
            ObjectMapper mapper = new ObjectMapper();

            try {
                return mapper.readValue(result.getBody(), new TypeReference<Map<String, Object>>() {
                });
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
		return null;

	}
	public static boolean deleteRecordByCode(String code,int objectType)
	{
        if (code != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.getForEntity(
                    getObjectTypeUrl(objectType) + "/" + code + "/delete",
                    String.class
            );
			try {
				return Utils.parseBoolean(result.getBody());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
		return false;
	}

	public static List<Object> listCurrentRecords(int objectType)
	{
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(
				getObjectTypeUrl(objectType) + "/list",
				String.class
		);
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(result.getBody(), new TypeReference<List<Object>>(){});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return new ArrayList<Object>();
	}
	public static List<ComboBoxItem> listCurrentRecordRefernces(int objectType)
	{
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(
				getObjectTypeUrl(objectType) + "/refs",
				String.class
		);
		ObjectMapper mapper = new ObjectMapper();

		try {
			List<Map<String, String>> res = mapper.readValue(result.getBody(), new TypeReference<List<Map<String, String>>>(){});
			List<ComboBoxItem> ret = new ArrayList<>();
			res.forEach(map->{
				if (!map.isEmpty()){
					Map.Entry<String, String> entry = map.entrySet().iterator().next();
					ret.add(new ComboBoxItem(entry.getKey(), entry.getValue()));
				}
			});
			return ret;
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return new ArrayList<ComboBoxItem>();
	}
	public static double getProductPrice(String productCode) {
        if (productCode != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.getForEntity(
                    getObjectTypeUrl(TYPE_PRODUCT) + "/" + productCode + "/price",
                    String.class
            );
            try {
                return Utils.parseDouble(result.getBody());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
		return 0;
	}


	private static String getObjectTypeUrl(int objectType){
		switch (objectType) {
			case TYPE_CUSTOMER : return Main.BACKEND_API_URL + "/customer";
			case TYPE_PRODUCT : return Main.BACKEND_API_URL + "/product";
			case TYPE_SALESORDER : return Main.BACKEND_API_URL + "/salesOrder";
		}
		return Main.BACKEND_API_URL;
	}

}
