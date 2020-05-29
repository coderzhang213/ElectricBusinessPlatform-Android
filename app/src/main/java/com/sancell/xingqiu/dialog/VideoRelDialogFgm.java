package com.sancell.xingqiu.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sancell.xingqiu.R;
import com.sancell.xingqiu.constants.IntentKey;
import com.sancell.xingqiu.entity.live.VideoRelationRes;
import com.sancell.xingqiu.mvvm.viewmodel.LiveViewModel;
import com.sancell.xingqiu.utils.ScreenUtil;
import com.sancell.xingqiu.view.live.adapter.LiveGoodRelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan_Xiong
 * @desc: 商品关联的dialog
 * @time 2019-11-21 21:07
 */
public class VideoRelDialogFgm extends BaseDialogFragment {

    private AppCompatTextView tv_title;
    private AppCompatImageView iv_close;
    private RecyclerView rv_common;
    private OnGoodsListener mGoodsListener;
    private OnTeamClickListener mTeamListener;

    private String mType;
    private List<VideoRelationRes.InfoList> mDatas;
    private int showHeight = ScreenUtil.dip2px(340);
    private String titleMsg = "";
    private View rl_not_data;//暂无数据视图
    private LiveViewModel mLiveViewModel;

    public static VideoRelDialogFgm newInstance(String type, List<VideoRelationRes.InfoList> res) {

        Bundle args = new Bundle();
        args.putString(IntentKey.KEY_VIDEO_REL_TYPE, type);
        args.putSerializable(IntentKey.KEY_VIDEO_REL_LIST, new ArrayList<>(res));
        VideoRelDialogFgm fragment = new VideoRelDialogFgm();
        fragment.setArguments(args);
        return fragment;
    }

    public void setDialogHeight(int height) {
        showHeight = height;
    }

    @Override
    protected void getData() {
        super.getData();
        mLiveViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);

        mType = getArguments().getString(IntentKey.KEY_VIDEO_REL_TYPE);
        mDatas = (List<VideoRelationRes.InfoList>) getArguments().getSerializable(IntentKey.KEY_VIDEO_REL_LIST);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_video_rel, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(titleMsg)) {
            tv_title.setText(titleMsg);
        }
        iv_close = view.findViewById(R.id.iv_close);
        rv_common = view.findViewById(R.id.rv_common);
        rl_not_data = view.findViewById(R.id.rl_not_data);
        if (mDatas == null || mDatas.size() <= 0) {//显示暂无数据
            rl_not_data.setVisibility(View.VISIBLE);
        }
        rv_common.setLayoutManager(new LinearLayoutManager(mContext));
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }

    public void setTv_title(String title) {
        this.titleMsg = title;

    }

    public void initData() {
        if (TextUtils.equals(mType, "1")) {
            VideoGoodRelAdapter goodAdapter = new VideoGoodRelAdapter(mDatas);
            goodAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.iv_add_com || view.getId() == R.id.rl_item) {
                        VideoRelationRes.InfoList infoList = mDatas.get(position);
                        if (infoList.stock > 0) {
                            //加入购物车
                            if (mGoodsListener != null) {
                                mGoodsListener.addCart(infoList.goodsId + "");
                            }
                        } else {//去调用增加库存接口

                            mLiveViewModel.addStock(infoList.goodsId + "");
                            dismiss();
                        }

                    }
                }
            });
            rv_common.setAdapter(goodAdapter);
        } else if (TextUtils.equals(mType, "2")) {//主播商品
            LiveGoodRelAdapter goodAdapter = new LiveGoodRelAdapter(mDatas);
            rv_common.setAdapter(goodAdapter);
        } else {
            //群组
        }

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(mContext, R.style.common_bottomDialog);
        dialog.setContentView(R.layout.dialog_video_rel);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_white_8));
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.height = showHeight;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.dimAmount = 0.55f;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        return dialog;
    }

    public interface OnGoodsListener {
        void addCart(String goodId);

        void goodDetail(String goodsId);
    }

    public void setGoodsListener(OnGoodsListener listener) {
        mGoodsListener = listener;
    }

    public interface OnTeamClickListener {

        void onApplyJoin(String teamId);

        void onChat(String teamId);
    }

    public void setTeamClickListener(OnTeamClickListener listener) {
        mTeamListener = listener;
    }
}
