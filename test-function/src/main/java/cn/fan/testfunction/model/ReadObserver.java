package cn.fan.testfunction.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者类
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/13 16:22
 */

public class ReadObserver implements Observer {

    private String name;

    private String article;

    public ReadObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        // 更新文章
        updateArticle(o);
    }

    private void updateArticle(Observable o) {
        JavaObservable javaStackObservable = (JavaObservable) o;
        this.article = javaStackObservable.getArticle();
        System.out.printf("我是读者：%s，文章已更新为：%s\n", this.name, this.article);
    }
}
