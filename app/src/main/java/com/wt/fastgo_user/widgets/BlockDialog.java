package com.wt.fastgo_user.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.wt.fastgo_user.R;

public class BlockDialog extends Dialog {
	private AnimationDrawable mLoadAnim;
	// 设置默认高度为160，宽度120，并且可根据屏幕像素密度自动进行大小调整
	public BlockDialog(Context context) {
		super(context, R.style.Dialog);
		getWindow().setContentView(R.layout.layout_block_dialog_demo);
		this.setCanceledOnTouchOutside(false);
		ImageView img = (ImageView)getWindow().findViewById(R.id.loading_icon);
		img.setBackgroundResource(R.drawable.loading_point);
		mLoadAnim = (AnimationDrawable) img.getBackground();
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		mLoadAnim.stop();
		super.dismiss();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		mLoadAnim.start();
		super.show();
	}

}
