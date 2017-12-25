package com.wt.fastgo_user.fragment;

import android.support.v4.app.Fragment;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.main.AddOrderFragment;
import com.wt.fastgo_user.fragment.main.AddTransportFragment;
import com.wt.fastgo_user.fragment.main.EditorFragment;
import com.wt.fastgo_user.fragment.main.LogisticsFragment;
import com.wt.fastgo_user.fragment.main.LogisticsResultFragment;
import com.wt.fastgo_user.fragment.main.MainFragment;
import com.wt.fastgo_user.fragment.main.ManagerFragment;
import com.wt.fastgo_user.fragment.main.OrderLibraryFragment;
import com.wt.fastgo_user.fragment.main.PackageFragment;
import com.wt.fastgo_user.fragment.main.PackageListFragment;
import com.wt.fastgo_user.fragment.main.PackageMyFragment;
import com.wt.fastgo_user.fragment.main.SystemFragment;
import com.wt.fastgo_user.fragment.main.TransFragment;
import com.wt.fastgo_user.fragment.main.TransferPayFragment;
import com.wt.fastgo_user.fragment.me.AddAddressFragment;
import com.wt.fastgo_user.fragment.me.AddCityFragment;
import com.wt.fastgo_user.fragment.me.AddDirectmailFragment;
import com.wt.fastgo_user.fragment.me.AddFeedbackFragment;
import com.wt.fastgo_user.fragment.me.AddInternationalFragment;
import com.wt.fastgo_user.fragment.me.AddressFragment;
import com.wt.fastgo_user.fragment.me.BalanceDetailFragment;
import com.wt.fastgo_user.fragment.me.BalanceFragment;
import com.wt.fastgo_user.fragment.me.CouponFragment;
import com.wt.fastgo_user.fragment.me.FeedbackFragment;
import com.wt.fastgo_user.fragment.me.FeedbackPackage;
import com.wt.fastgo_user.fragment.me.IntegralDetailFragment;
import com.wt.fastgo_user.fragment.me.IntegralFragment;
import com.wt.fastgo_user.fragment.me.MeFragment;
import com.wt.fastgo_user.fragment.me.OrderFragment;
import com.wt.fastgo_user.fragment.me.OrderMailFragment;
import com.wt.fastgo_user.fragment.me.OrderPayFragment;
import com.wt.fastgo_user.fragment.me.RechargeFragment;
import com.wt.fastgo_user.fragment.me.SafeFragment;
import com.wt.fastgo_user.fragment.me.SafeMailFragment;
import com.wt.fastgo_user.fragment.me.SafePasswordFragment;
import com.wt.fastgo_user.fragment.me.SafePhoneFragment;
import com.wt.fastgo_user.fragment.me.SettingFragment;
import com.wt.fastgo_user.fragment.me.StatisticsFragment;
import com.wt.fastgo_user.fragment.me.WithdrawalsFragment;
import com.wt.fastgo_user.fragment.message.AddBoardFragment;
import com.wt.fastgo_user.fragment.message.BoardFragment;
import com.wt.fastgo_user.fragment.packages.CentreFragment;
import com.wt.fastgo_user.fragment.packages.LocalMainFragment;
import com.wt.fastgo_user.fragment.packages.WarehouseFragment;
import com.wt.fastgo_user.fragment.service.CommonFragment;
import com.wt.fastgo_user.fragment.service.ServiceFragment;
import com.wt.fastgo_user.fragment.shop.ConsumeFragment;
import com.wt.fastgo_user.fragment.shop.OrderDetailFragment;
import com.wt.fastgo_user.fragment.shop.ShopFragment;
import com.wt.fastgo_user.fragment.shop.SuppliesFragment;
import com.wt.fastgo_user.ui.RegisterActivity;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class FragmentFactory {
    /**
     * main
     *
     * @param position
     * @return
     */
    public static Fragment createForMain(int position,String type) {
        Fragment fragment = null;
        switch (position) {
            case 0:// 国际转运
                fragment = new MainFragment();
                break;
            case 1:// 本地直邮
                fragment = new LocalMainFragment();
                break;
            case 2:// 海淘留言板
                fragment = new BoardFragment();
                break;
            case 3:// 服务
                fragment = new ServiceFragment();
                break;
            case 4:// 我
                if (type.equals("2")){
                    fragment = new ShopFragment();
                }else {
                    fragment = new MeFragment();
                }
//

                break;
        }
        return fragment;
    }

    public static Fragment createByLogin(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case R.id.text_login_register:// 注册
                fragment = new RegisterActivity();
                break;
            case R.id.linear_home_logistics:// 包裹打包
                fragment = new PackageFragment();
                break;
            case R.id.ben_order_setup://创建运单
                fragment = new AddOrderFragment();
                break;
            case R.id.linear_home_center://添加转运包裹
                fragment = new AddTransportFragment();
                break;
            case R.id.text_package_add://我的运单
                fragment = new PackageMyFragment();
                break;
            case R.id.btn_order_payment://付款
                fragment = new TransferPayFragment();
                break;
            case R.id.linear_home_have_to://物流查询
                fragment = new LogisticsFragment();
                break;
            case R.id.btn_logistics_query://查询物流结果
                fragment = new LogisticsResultFragment();
                break;
            case R.id.linear_home_been:// 包裹管理
                fragment = new ManagerFragment();
                break;
            case R.id.relative_service_common:// 常见问题
                fragment = new CommonFragment();
                break;
            case R.id.relative_home_question:// 常见问题
                fragment = new CommonFragment();
                break;
            case R.id.btn_order_editor:// 编辑包裹
                fragment = new EditorFragment();
                break;
            case R.id.linear_home_transfer:// 中转地址
                fragment = new TransFragment();
                break;
            case R.id.relative_home_message:// 系统消息
                fragment = new SystemFragment();
                break;
            case R.id.image_me_setting:// 设置
                fragment = new SettingFragment();
                break;
            case R.id.linear_me_coupon:// 我的优惠券
                fragment = new CouponFragment();
                break;
            case R.id.relative_me_address:// 我的收货地址
                fragment = new AddressFragment();
                break;
            case R.id.btn_address_add:// 添加收货地址
                fragment = new AddCityFragment();
                break;
            case R.id.btn_directmail_add:// 添加收货地址
                fragment = new AddAddressFragment();
                break;
            case R.id.linear_me_balance://我的余额
                fragment = new BalanceFragment();
                break;
            case R.id.text_balance_detail://余额明细
                fragment = new BalanceDetailFragment();
                break;
            case R.id.btn_balance_recharge://充值
                fragment = new RechargeFragment();
                break;
            case R.id.btn_balance_withdrawals://提现
                fragment = new WithdrawalsFragment();
                break;

            case R.id.linear_my_integral://我的积分
                fragment = new IntegralFragment();
                break;
            case R.id.text_integral_detail://积分明细
                fragment = new IntegralDetailFragment();
                break;
            case R.id.relative_shop_order://我的订单
                fragment = new OrderFragment();
                break;
            case R.id.relative_my_statistics://包裹统计
                fragment = new StatisticsFragment();
                break;
            case R.id.relative_my_feedback://问题反馈
                fragment = new FeedbackFragment();
                break;
            case R.id.btn_feedback_add://添加问题反馈
                fragment = new AddFeedbackFragment();
                break;
            case R.id.ben_order_payment://立即付款
                fragment = new OrderPayFragment();
                break;
            case R.id.relative_my_safe://安全中心
                fragment = new SafeFragment();
                break;
            case R.id.relative_safe_mail://邮箱绑定
                fragment = new SafeMailFragment();
//                fragment = new SafeChangeMailFragment();//修改邮箱
                break;
            case R.id.relative_safe_phone://修改手机号
                fragment = new SafePhoneFragment();
                break;
            case R.id.relative_safe_password://修改密码
                fragment = new SafePasswordFragment();
                break;
            case R.id.relative_shop_supplies://耗材使用统计
                fragment = new SuppliesFragment();
                break;
            case R.id.relative_shop_consume://消费统计
                fragment = new ConsumeFragment();
                break;
            case -1://订单详情
                fragment = new OrderDetailFragment();
                break;
            case R.id.text_board_add://添加留言
                fragment = new AddBoardFragment();
                break;
            case R.id.linear_local_warehouse://小仓库
                fragment = new WarehouseFragment();
                break;
        }
        return fragment;
    }


    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createForConsume(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case 0:// 寄往中转仓
                fragment = new CentreFragment();
                break;
            case 1:// 转运包裹
                fragment = new CentreFragment();
//                fragment = new TransferFragment();
                break;
        }
        return fragment;
    }

    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createForAddress(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case 0:// 收件人
                fragment = AddDirectmailFragment.newInstance(1);
                break;
            case 1:// 发件人
                fragment = AddDirectmailFragment.newInstance(2);
                break;
        }
        return fragment;
    }

    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createForAdd(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case 0:// 收件人
                fragment = AddInternationalFragment.newInstance(0);
                break;
            case 1:// 发件人
                fragment = AddInternationalFragment.newInstance(1);
                break;
        }
        return fragment;
    }

    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createFororder(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case 0:// 全部
                fragment = new OrderMailFragment();
                break;
            case 1:// 未入库
                fragment = new OrderMailFragment();
                break;
            case 2:// 已入库
                fragment = new OrderMailFragment();
                break;
            case 3:// 待付款
                fragment = new OrderMailFragment();
                break;
            case 4:// 已付款
                fragment = new OrderMailFragment();
                break;
            case 5:// 退单
                fragment = new OrderMailFragment();
                break;
            case 6:// 国内派送
                fragment = new OrderMailFragment();
                break;
        }
        return fragment;
    }
    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createForManager(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case 0:// 全部
                fragment = new OrderLibraryFragment();
                break;
            case 1:// 未入库
                fragment = new OrderLibraryFragment();
                break;
            case 2:// 已入库
                fragment = new OrderLibraryFragment();
                break;
            case 3:// 待付款
                fragment = new OrderLibraryFragment();
                break;
            case 4:// 已付款
                fragment = new OrderLibraryFragment();
                break;
        }
        return fragment;
    }
    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createForFeedback(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case 0:// 包裹问题反馈
                fragment = new FeedbackPackage();
                break;
            case 1:// 订单问题反馈
                fragment = new FeedbackPackage();
                break;
            case 2:// 其他问题反馈
                fragment = new FeedbackPackage();
                break;
        }
        return fragment;
    }
    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createForPackage(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case 0:// 包裹问题反馈
                fragment = new PackageListFragment();
                break;
            case 1:// 订单问题反馈
                fragment = new PackageListFragment();
                break;
            case 2:// 其他问题反馈
                fragment = new PackageListFragment();
                break;
        }
        return fragment;
    }
}
