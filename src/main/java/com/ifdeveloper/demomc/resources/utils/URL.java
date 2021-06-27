package com.ifdeveloper.demomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

public class URL {
	
	public static String decodificarParametro(String parametroURL) {
		try {
			return URLDecoder.decode(parametroURL, "UTF-8").toLowerCase();
		} catch (UnsupportedEncodingException e) {
			return Strings.EMPTY;
		}
	}
	
	public static List<Integer> converterListaInteiros(String url) {
		
		List<Integer> listaIds = new ArrayList<>();
		try {
			String[] vetor = url.split(",");
			
			for (String item : vetor) {
				listaIds.add(Integer.valueOf(item));
			}
			
			return listaIds;
		} catch (Exception e) {
			return listaIds;
		}
	}

}
