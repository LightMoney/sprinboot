package cn.fan.testfunction.model;

import lombok.Data;

import java.util.Observable;

/**
 * 观察类
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/13 16:19
 */
@Data
public class JavaObservable extends Observable {

    private String article;

    /**
     * 发表文章
     * @param article
     */
    public void publish(String article){
        // 发表文章
        this.article = article;

        // 改变状态
        this.setChanged();

        // 通知所有观察者
        this.notifyObservers();
    }
}
