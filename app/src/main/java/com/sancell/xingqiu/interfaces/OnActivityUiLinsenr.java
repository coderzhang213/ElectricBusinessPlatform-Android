package com.sancell.xingqiu.interfaces;


public interface OnActivityUiLinsenr {
    void showInputPanel();
    void onStartLivingFinished();
    void onLiveDisconnect();
    void normalFinishLive();
    void onStartLivePlay();
    //是否精简模式
    void onShowStreamline(boolean isShow);
    void hideSendMsg();
    //简洁模式
    void isShowStreamline(boolean isShow);
    void inputMsg();
}
