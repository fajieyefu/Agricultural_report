package fajieyefu.com.agricultural_report.bean;

/**
 * Created by qiancheng on 2016/11/23.
 */
public class BillInfo {
	private String bill_code;
	private String bill_title;
	private String bill_flag;
	private String apply_date;
	private String apply_area;

	public BillInfo(String apply_area, String apply_date, String bill_code,
					String bill_flag, String bill_title) {
		this.apply_area = apply_area;
		this.apply_date = apply_date;
		this.bill_code = bill_code;
		this.bill_flag = bill_flag;
		this.bill_title = bill_title;
	}

	public String getApply_area() {
		return apply_area;
	}

	public void setApply_area(String apply_area) {
		this.apply_area = apply_area;
	}

	public String getApply_date() {
		return apply_date;
	}

	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}

	public String getBill_code() {
		return bill_code;
	}

	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}

	public String getBill_flag() {
		return bill_flag;
	}

	public void setBill_flag(String bill_flag) {
		this.bill_flag = bill_flag;
	}

	public String getBill_title() {
		return bill_title;
	}

	public void setBill_title(String bill_title) {
		this.bill_title = bill_title;
	}
}
