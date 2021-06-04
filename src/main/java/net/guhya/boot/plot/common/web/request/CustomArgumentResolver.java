package net.guhya.boot.plot.common.web.request;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.guhya.boot.plot.common.web.AbstractRestController;
import net.guhya.boot.plot.common.web.WebUtil;

@Component
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static Logger log = LoggerFactory.getLogger(CustomArgumentResolver.class);
	
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(Box.class);
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public Object resolveArgument(MethodParameter methodParameter, 
								ModelAndViewContainer mavContainer,
								NativeWebRequest webRequest, 
								WebDataBinderFactory binderFactory) throws Exception {
		
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		Box paramBox = new Box();
		
		//Extract form/querystring parameters to hashmap and bind them in a box		
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			log.info("### Param [" + key + "] : ["+request.getParameter(key)+"]");
			String[] values = request.getParameterValues(key);
			if (values != null) {
				paramBox.put(key, (values.length > 1) ? values : values[0]);
			}
		}
		
		//Multipart Files
		if(request instanceof MultipartHttpServletRequest){
			List<Map<String, MultipartFile>> fileList = WebUtil.getFilesFromRequest(request);
			paramBox.put(AbstractRestController.FILE_LIST, fileList);
		}else{
			paramBox.put(AbstractRestController.FILE_LIST, new ArrayList<Object>());			
		}
		
		//All fields in request variable are string, match type
		paramBox = postProcessBox(request, paramBox);		
		log.info("### Box : " + paramBox.toString());
		
		return paramBox;
	}
	
	private Box postProcessBox(HttpServletRequest request, Box paramBox) throws Exception{
		
		//When item on the list is clicked, bring all parameters along
		if(request.getQueryString() != null){
			paramBox.put(AbstractRestController.PARAM_QUERY_STRING	, request.getQueryString());
		}		

		// Set URI
		paramBox.put("URI", request.getRequestURI());
				
		return paramBox;
	}
}