package cat.reisigualada.reis.utils;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {
	
	@JsonView(Views.Public.class)
	String msg;
	
	@JsonView(Views.Public.class)
	String code;
	
	@SuppressWarnings("rawtypes")
	@JsonView(Views.Public.class)
	List result;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@SuppressWarnings("rawtypes")
	public List getResult() {
		return result;
	}
	@SuppressWarnings("rawtypes")
	public void setResult(List result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AjaxResponseBody [msg=");
		builder.append(msg);
		builder.append(", code=");
		builder.append(code);
		builder.append(", result=");
		builder.append(result);
		builder.append("]");
		return builder.toString();
	}
}
