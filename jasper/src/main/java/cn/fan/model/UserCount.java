package cn.fan.model;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/6/3 9:42
 */
public class UserCount {
    private  String company;
    private  Long count;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public UserCount(String company, Long count) {
        this.company = company;
        this.count = count;
    }
}
