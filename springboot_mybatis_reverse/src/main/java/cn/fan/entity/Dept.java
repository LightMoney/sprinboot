package cn.fan.entity;

import java.io.Serializable;

public class Dept implements Serializable {
    private Integer deptno;

    private String dname;

    private String dbSource;

    private static final long serialVersionUID = 1L;

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname == null ? null : dname.trim();
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource == null ? null : dbSource.trim();
    }
}