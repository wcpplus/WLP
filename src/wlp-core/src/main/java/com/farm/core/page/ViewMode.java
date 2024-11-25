package com.farm.core.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

/**
 * ajax请求时的json模型
 * 
 *
 */
public class ViewMode {
	private final static Logger log = Logger.getLogger(ViewMode.class);
	private Map<String, Object> attrs = new HashMap<String, Object>();

	public static ViewMode getInstance() {
		ViewMode obj = new ViewMode();
		obj.attrs.put("STATE", StateType.SUCCESS.value);
		obj.attrs.put("OPERATE", OperateType.OTHER.value);
		return obj;
	}

	/**
	 * 装入json返回值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ViewMode putAttr(String key, String value) {
		attrs.put(key, value);
		return this;
	}

	public ViewMode putAttr(String key, Object value) {
		attrs.put(key, value);
		return this;
	}

	public ViewMode putAttrs(Map<String, Object> map) {
		attrs.putAll(map);
		return this;
	}

	/**
	 * 装入错误
	 * 
	 * @param message
	 *            错误消息
	 * @return
	 */
	public ViewMode setError(String message, Exception exception) {
		if (message == null || message.isEmpty()) {
			message = "该错误无可读信息，请管理员登陆服务器查看错误日志!";
		}
		if (message != null && message.indexOf("SQLException") >= 0
				&& getStackTrace(exception).indexOf("Incorrect string value: '\\x") > 0) {
			// mysql的utf-8编码可能2个字节、3个字节、4个字节的字符，但是MySQL的utf8编码只支持3字节的数据，
			// 而移动端的表情数据是4个字节的字符。如果直接往采用utf-8编码的数据库中插入表情数据，Java程序中将报SQL异常
			message = "本系统不支持emoji表情字符,请删除后重新提交!";
		}
		attrs.put("MESSAGE", message);
		attrs.put("STATE", StateType.ERROR.value);
		if (exception != null) {
			log.error(message, exception);
		} else {
			log.error(message);
		}
		return this;
	}

	/**
	 * 获得异常的堆栈信息
	 * 
	 * @param throwable
	 * @return
	 */
	private static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			throwable.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
			try {
				sw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ViewMode setOperate(OperateType type) {
		attrs.put("OPERATE", type.value);
		return this;
	}

	/**
	 * 返回map格式json
	 * 
	 * @return
	 */
	public Map<String, Object> returnObjMode() {
		return attrs;
	}

	/**
	 * 返回json結果封裝
	 * 
	 * @return
	 */
	public ResponseEntity<Map<String, Object>> returnJsonMode() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<Map<String, Object>>(attrs, headers, HttpStatus.OK);
	}

	/**
	 * 返回spring的ModelAndView(如jsp)
	 * 
	 * @param path
	 * @return
	 */
	public ModelAndView returnModelAndView(String path) {
		log.debug(path);
		return new ModelAndView(path, this.returnObjMode());
	}

	/**
	 * 重定向到URL地址
	 * 
	 * @param path
	 * @return
	 */
	public ModelAndView returnRedirectUrl(String path) {
		String paras = null;
		for (String name : attrs.keySet()) {
			if (attrs.get(name) == null) {
				continue;
			}
			String val = attrs.get(name).toString();
			// 过滤特殊字符，在重定向时，不要序列（通过符号[]判断）
			if (val.indexOf("[") < 0 && val.indexOf("]") < 0) {
				if (paras == null) {
					paras = name + "=" + val;
				} else {
					paras = paras + "&" + name + "=" + val;
				}
			}
		}
		if (paras == null) {
			paras = "";
		}
		if (path.indexOf("?") > 0) {
			path = path + "&" + paras;
		} else {
			if (!paras.equals("")) {
				path = path + "?" + paras;
			}
		}
		return new ModelAndView("redirect:" + path);
	}

	/**
	 * 重定向到URL地址(不带入参path以外的参数)
	 * 
	 * @param path
	 * @return
	 */
	public ModelAndView returnRedirectOnlyUrl(String path) {
		return new ModelAndView("redirect:" + path);
	}

	/**
	 * 返回文本格式json(暂未实现)
	 * 
	 * @return
	 */
	@Deprecated
	public String returnStrMode() {
		// TODO
		return "String";
	}

	public static List<?> returnListObjMode(List<?> list) {
		return list;
	}

}
