<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<form action="/pay" method="post">
    订单号：<input type="text" name="WIDout_trade_no" required><br/>
    订单名称：<input type="text" name="WIDsubject" required><br/>
    付款金额：<input type="text" name="WIDtotal_amount" required><br/>
    WIDbody：<input type="text" name="WIDbody"><br/>
    <input type="submit" value="下单"> <input type="reset" value="重置">
</form>
