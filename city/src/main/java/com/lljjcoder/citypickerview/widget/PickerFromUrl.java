package com.lljjcoder.citypickerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.citypickerview.R;
import com.lljjcoder.citypickerview.model.CityModel;
import com.lljjcoder.citypickerview.model.DbInfo;
import com.lljjcoder.citypickerview.model.DistrictModel;
import com.lljjcoder.citypickerview.model.ProvinceModel;
import com.lljjcoder.citypickerview.utils.DataUtil;
import com.lljjcoder.citypickerview.utils.DbHelper;
import com.lljjcoder.citypickerview.utils.Key;
import com.lljjcoder.citypickerview.utils.XmlParserHandler;
import com.lljjcoder.citypickerview.widget.wheel.OnWheelChangedListener;
import com.lljjcoder.citypickerview.widget.wheel.WheelView;
import com.lljjcoder.citypickerview.widget.wheel.adapters.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 从网络上获取数据本存入本地数据库 先缓存后本地存储
 */

public class PickerFromUrl implements CanShow, OnWheelChangedListener {

    private Context context;

    private PopupWindow popwindow;

    private View popview;

    private WheelView oneView;

    private WheelView twoView;

    private WheelView threeView;

    private RelativeLayout mRelativeTitleBg;

    private TextView mTvOK;

    private TextView mTvTitle;

    private TextView mTvCancel;

    /**
     * 一级目录
     */
    protected List<DbInfo> oneList;

    /**
     * key - 一级 value - 二级
     */
    protected Map<Integer, List<DbInfo>> twoMap = new HashMap<>();

    /**
     * key - 二级 values - 三级
     */
    protected Map<Integer, List<DbInfo>> threeMap = new HashMap<>();

    /**
     * key - 三级 values - 四级
     */
    protected Map<Integer, DbInfo> thirdMap = new HashMap<>();

    /**
     * 当前一级目录名称
     */
    private String oneName;
    private int oneId;

    /**
     * 当前二级目录
     */
    private String twoName;
    private int twoId;

    /**
     * 当前三级
     */
    private String threeName = "";
    private int threeId;

    /**
     * 当前四级
     */
    private String siName = "";
    private int siId = 0;

    private OnCityItemClickListener listener;

    public interface OnCityItemClickListener {
        void onSelected(String... citySelected);

        void onSelectedId(Integer... sel);

        void onCancel();
    }

    public void setOnCityItemClickListener(OnCityItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Default text color
     */
    private static final int DEFAULT_TEXT_COLOR = 0xFF585858;

    /**
     * Default text size
     */
    private static final int DEFAULT_TEXT_SIZE = 16;

    // Text settings
    private int textColor = DEFAULT_TEXT_COLOR;

    private int textSize = DEFAULT_TEXT_SIZE;

    /**
     * 选中的字体颜色
     */
    private int selecterColor = DEFAULT_TEXT_COLOR;


    /**
     * 滚轮显示的item个数
     */
    private static final int DEF_VISIBLE_ITEMS = 5;

    // Count of visible items
    private int visibleItems = DEF_VISIBLE_ITEMS;

    /**
     * 省滚轮是否循环滚动
     */
    private boolean isProvinceCyclic = true;

    /**
     * 市滚轮是否循环滚动
     */
    private boolean isCityCyclic = true;

    /**
     * 区滚轮是否循环滚动
     */
    private boolean isDistrictCyclic = true;

    /**
     * item间距
     */
    private int padding = 5;


    /**
     * Color.BLACK
     */
    private int cancelTextColorStr = android.R.color.black;


    /**
     * Color.BLUE
     */
    private int confirmTextColorStr = android.R.color.holo_blue_light;

    /**
     * 标题背景颜色
     */
    private int titleBackgroundColorStr = android.R.color.black;
    /**
     * 标题颜色
     */
    private int titleTextColorStr = android.R.color.black;

    /**
     * 第一次默认的显示省份，一般配合定位，使用
     */
    private String defaultProvinceName = "";

    /**
     * 第一次默认得显示城市，一般配合定位，使用
     */
    private String defaultCityName = "";

    /**
     * 第一次默认得显示，一般配合定位，使用
     */
    private String defaultDistrict = "";

    /**
     * 两级联动
     */
    private boolean showProvinceAndCity = false;

    private boolean showOne = false;

    /**
     * 标题
     */
    private String mTitle = "选择地区";

    /**
     * 设置popwindow的背景
     */
    private int backgroundPop = 0xa0000000;

    private int type;

    private PickerFromUrl(Builder builder, int type, List<DbInfo> dataList, Map<Integer, List<DbInfo>> map) {
        this.twoMap = map;
        this.type = type;
        this.textColor = builder.textColor;
        this.selecterColor = builder.selectorColor;
        this.textSize = builder.textSize;
        this.visibleItems = builder.visibleItems;
        this.isProvinceCyclic = builder.isProvinceCyclic;
        this.isDistrictCyclic = builder.isDistrictCyclic;
        this.isCityCyclic = builder.isCityCyclic;
        this.context = builder.mContext;
        this.padding = builder.padding;
        this.mTitle = builder.mTitle;
        this.titleBackgroundColorStr = builder.titleBackgroundColorStr;
        this.confirmTextColorStr = builder.confirmTextColorStr;
        this.cancelTextColorStr = builder.cancelTextColorStr;

        this.defaultDistrict = builder.defaultDistrict;
        this.defaultCityName = builder.defaultCityName;
        this.defaultProvinceName = builder.defaultProvinceName;

        this.showProvinceAndCity = builder.showProvinceAndCity;
        this.backgroundPop = builder.backgroundPop;
        this.titleTextColorStr = builder.titleTextColorStr;
        this.showOne = builder.showOne;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popview = layoutInflater.inflate(R.layout.pop_citypicker, null);

        oneView = (WheelView) popview.findViewById(R.id.id_province);
        twoView = (WheelView) popview.findViewById(R.id.id_city);
        threeView = (WheelView) popview.findViewById(R.id.id_district);
        mRelativeTitleBg = (RelativeLayout) popview.findViewById(R.id.rl_title);
        mTvOK = (TextView) popview.findViewById(R.id.tv_confirm);
        mTvTitle = (TextView) popview.findViewById(R.id.tv_title);
        mTvCancel = (TextView) popview.findViewById(R.id.tv_cancel);

        popwindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popwindow.setBackgroundDrawable(new ColorDrawable(backgroundPop));
        popwindow.setAnimationStyle(R.style.AnimBottom);
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(false);
        popwindow.setFocusable(true);


        /**
         * 设置标题背景颜色
         */
        if (this.titleBackgroundColorStr != 0) {
            mRelativeTitleBg.setBackgroundColor(ActivityCompat.getColor(context, this.titleBackgroundColorStr));

        }

        /**
         * 设置标题
         */
        if (!TextUtils.isEmpty(this.mTitle)) {
            mTvTitle.setText(this.mTitle);
        }


        /**
         *  设置确认按钮文字颜色
         */

        if (this.titleTextColorStr != 0) {
            mTvTitle.setTextColor(ActivityCompat.getColor(context, titleTextColorStr));
        }


        // 设置确认按钮文字颜色
        if (this.confirmTextColorStr != 0) {
            mTvOK.setTextColor(ActivityCompat.getColor(context, this.confirmTextColorStr));
        }

        //设置取消按钮文字颜色
        if (this.cancelTextColorStr != 0) {
            mTvCancel.setTextColor(ActivityCompat.getColor(context, this.cancelTextColorStr));
        }


        if (this.showOne) {
            twoView.setVisibility(View.GONE);
            threeView.setVisibility(View.GONE);
        } else {
            //只显示省市两级联动
            if (this.showProvinceAndCity) {
                threeView.setVisibility(View.GONE);
            } else {
                threeView.setVisibility(View.VISIBLE);
            }
        }
        //初始化一级目录
        initOneDatas(context, type, dataList);

        // 添加change事件
        oneView.addChangingListener(this);
        // 添加change事件
        twoView.addChangingListener(this);
        // 添加change事件
        threeView.addChangingListener(this);
        // 添加onclick事件
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                hide();
            }
        });

        mTvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (showOne) {
                        // 一级联动
                        listener.onSelected(oneName, twoName, "", "");
                        listener.onSelectedId(oneId, 0, 0, 0);
                    } else {
                        if (showProvinceAndCity) {
                            // 两级联动
                            listener.onSelected(oneName, twoName, "", "");
                            listener.onSelectedId(oneId, twoId, 0, 0);
                        } else {
                            // 三级联动
                            listener.onSelected(oneName, twoName, threeName, siName);
                            listener.onSelectedId(oneId, twoId, threeId, siId);
                        }
                    }
                }

                hide();
            }
        });

    }


    public static class Builder {

        /**
         * Default text color
         */
        public static final int DEFAULT_TEXT_COLOR = android.R.color.black;

        /**
         * Default text size
         */
        public static final int DEFAULT_TEXT_SIZE = 18;


        private int textColor = DEFAULT_TEXT_COLOR;
        private int selectorColor = DEFAULT_TEXT_COLOR;


        private int textSize = DEFAULT_TEXT_SIZE;

        /**
         * 滚轮显示的item个数
         */
        private static final int DEF_VISIBLE_ITEMS = 5;

        // Count of visible items
        private int visibleItems = DEF_VISIBLE_ITEMS;

        /**
         * 省滚轮是否循环滚动
         */
        private boolean isProvinceCyclic = true;

        /**
         * 市滚轮是否循环滚动
         */
        private boolean isCityCyclic = true;

        /**
         * 区滚轮是否循环滚动
         */
        private boolean isDistrictCyclic = true;

        private Context mContext;

        /**
         * item间距
         */
        private int padding = 5;


        /**
         * Color.BLACK
         */
        private int cancelTextColorStr = android.R.color.black;


        /**
         * Color.BLUE
         */
        private int confirmTextColorStr = android.R.color.holo_blue_light;

        /**
         * 标题背景颜色
         */
        private int titleBackgroundColorStr = android.R.color.white;

        /**
         * 标题颜色
         */
        private int titleTextColorStr = android.R.color.black;


        /**
         * 第一次默认的显示省份，一般配合定位，使用
         */
        private String defaultProvinceName = "青铜";

        /**
         * 第一次默认得显示城市，一般配合定位，使用
         */
        private String defaultCityName = "V";

        /**
         * 第一次默认得显示，一般配合定位，使用
         */
        private String defaultDistrict = "0星";

        /**
         * 标题
         */
        private String mTitle = "选择地区";

        /**
         * 两级联动，默认为两级联动
         */
        private boolean showProvinceAndCity = true;

        private boolean showOne = false;

        /**
         * 设置是否只有一级滑动
         *
         * @param b
         * @return
         */
        public Builder setShowOne(boolean b) {
            this.showOne = b;
            return this;

        }

        /**
         * 设置popwindow的背景
         */
        private int backgroundPop = 0xa0000000;


        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * 设置popwindow的背景
         */
        public Builder backgroundPop(int backgroundPopColor) {
            this.backgroundPop = backgroundPopColor;
            return this;
        }

        /**
         * 设置标题背景颜色
         */
        public Builder titleBackgroundColor(int colorBg) {
            this.titleBackgroundColorStr = colorBg;
            return this;
        }

        /**
         * 设置标题背景颜色
         */
        public Builder titleTextColor(int titleTextColorStr) {
            this.titleTextColorStr = titleTextColorStr;
            return this;
        }


        /**
         * 设置标题
         */
        public Builder title(String mtitle) {
            this.mTitle = mtitle;
            return this;
        }

        /**
         * 是否只显示省市两级联动
         */
        public Builder onlyShowProvinceAndCity(boolean flag) {
            this.showProvinceAndCity = flag;
            return this;
        }

        /**
         * 第一次默认的显示省份，一般配合定位，使用
         */
        public Builder province(String defaultProvinceName) {
            this.defaultProvinceName = defaultProvinceName;
            return this;
        }

        /**
         * 第一次默认得显示城市，一般配合定位，使用
         */
        public Builder city(String defaultCityName) {
            this.defaultCityName = defaultCityName;
            return this;
        }

        /**
         * 第一次默认地区显示，一般配合定位，使用
         */
        public Builder district(String defaultDistrict) {
            this.defaultDistrict = defaultDistrict;
            return this;
        }


        /**
         * 确认按钮文字颜色
         */
        public Builder confirTextColor(int color) {
            this.confirmTextColorStr = color;
            return this;
        }


        /**
         * 取消按钮文字颜色
         */
        public Builder cancelTextColor(int color) {
            this.cancelTextColorStr = color;
            return this;
        }

        /**
         * item文字颜色
         */
        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }


        public Builder setSelectorColor(int textColor) {
            this.selectorColor = textColor;
            return this;
        }

        /**
         * item文字大小
         */
        public Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 滚轮显示的item个数
         */
        public Builder visibleItemsCount(int visibleItems) {
            this.visibleItems = visibleItems;
            return this;
        }

        /**
         * 省滚轮是否循环滚动
         */
        public Builder provinceCyclic(boolean isProvinceCyclic) {
            this.isProvinceCyclic = isProvinceCyclic;
            return this;
        }

        /**
         * 市滚轮是否循环滚动
         */
        public Builder cityCyclic(boolean isCityCyclic) {
            this.isCityCyclic = isCityCyclic;
            return this;
        }


        /**
         * 区滚轮是否循环滚动
         */
        public Builder districtCyclic(boolean isDistrictCyclic) {
            this.isDistrictCyclic = isDistrictCyclic;
            return this;
        }

        /**
         * item间距
         */
        public Builder itemPadding(int itemPadding) {
            this.padding = itemPadding;
            return this;
        }


        public PickerFromUrl build(int type, List<DbInfo> list, Map<Integer, List<DbInfo>> map) {

            PickerFromUrl cityPicker = new PickerFromUrl(this, type, list, map);
            return cityPicker;
        }

    }

    public int indexType = 0;

    public int getIndexType() {
        return indexType;
    }

    public void setIndexType(int indexType) {
        this.indexType = indexType;
    }

    private ArrayWheelAdapter arrayWheelAdapter;

    private void setUpData() {
        int provinceDefault = -1;
        int len = oneList.size();
        if (!TextUtils.isEmpty(defaultProvinceName) && len > 0) {
            for (int i = 0; i < len; i++) {
                DbInfo info = oneList.get(i);
                if (info.getText().contains(defaultProvinceName)) {
                    provinceDefault = i;
                    break;
                }
            }
        }

        String[] mProvinceDatas = new String[len];
        for (int i = 0; i < len; i++) {
            mProvinceDatas[i] = oneList.get(i).getText();
        }

        arrayWheelAdapter = new ArrayWheelAdapter<String>(context, mProvinceDatas);

        oneView.setViewAdapter(arrayWheelAdapter);
        //获取所设置的省的位置，直接定位到该位置
        if (-1 != provinceDefault) {
            oneView.setCurrentItem(provinceDefault);
        }

        // 设置可见条目数量
        oneView.setVisibleItems(visibleItems);
        twoView.setVisibleItems(visibleItems);
        threeView.setVisibleItems(visibleItems);
        oneView.setCyclic(isProvinceCyclic);
        twoView.setCyclic(isCityCyclic);
        threeView.setCyclic(isDistrictCyclic);

        arrayWheelAdapter.setPadding(padding);
        arrayWheelAdapter.setTextColor(textColor);
        arrayWheelAdapter.setTextSize(textSize);
        arrayWheelAdapter.setCheckedTextColor(selecterColor);

        setTwo();

    }


    /**
     * 显示二级目录
     */
    private void setTwo() {
        int pCurrent = oneView.getCurrentItem();
        if (oneList.size() != 0) {
            oneName = oneList.get(pCurrent).getText();
            oneId = oneList.get(pCurrent).getId();

//            List<DbInfo> two = DataUtil.getInstance(context).getTwo(oneId, type);
            // 本地存在

            List<DbInfo> two = twoMap.get(oneId);

            if (two != null && two.size() != 0) {

//                twoMap.put(oneId, two);

                updateCities(two);

            } else {
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(2, oneId);

            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class MyAsyncTask extends AsyncTask<Integer, Void, Key> {

        int code;

        @Override
        protected Key doInBackground(Integer... str) {
            this.code = str[0];
            // 从网络获取
            Key key = null;
            if (code == 2) {
                key = getDataFromUrl.getFromUrlTwo(str[1]);
            } else if (code == 3) {
                key = getDataFromUrl.getFromUrlThree(str[1], str[2]);
            }

            return key;

        }

        @Override
        protected void onPostExecute(Key key) {
            super.onPostExecute(key);
            List<DbInfo> list = key.getList();
            if (code == 2) {

//                DataUtil.getInstance(context).saveTwoData(key);

                twoMap.put(key.getKey(), list);
                updateCities(list);

            } else if (code == 3) {
                //从网络获取
//                DataUtil.getInstance(context).saveThree(key);

                threeMap.put(key.getKey(), list);
                updateAreas(list);
            }
        }
    }


    private void setThree() {
        int pp = oneView.getCurrentItem();
        int oneId = oneList.get(pp).getId();
        List<DbInfo> tt = twoMap.get(oneId);

        int p = twoView.getCurrentItem();
        if (tt != null && tt.size() != 0) {
            twoName = tt.get(p).getText();
            twoId = tt.get(p).getId();
            List<DbInfo> threeList = DataUtil.getInstance(context).getThree(oneId, twoId, type);
            if(threeList.size()==0){
                threeList=threeMap.get(twoId);
            }
            if (threeList != null && threeList.size() != 0) {
                threeMap.put(twoId, threeList);
                updateAreas(threeList);
            } else {
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(3, oneId, twoId);

            }
        }
    }


    /**
     * 获取一级目录
     *
     * @param context 上下文对象
     * @param type    类型
     */
    private void initOneDatas(Context context, int type, List<DbInfo> dataList) {

//        //  获取数据一级数据
//        List<DbInfo> list = DataUtil.getInstance(context).getOne(type);
//        if (list != null && list.size() != 0) {
//            this.oneList = list;
//        } else {
//
        this.oneList = dataList;

//        }

    }


    private ArrayWheelAdapter districtWheel;

    /**
     * 根据二级获取三级
     */
    private void updateAreas(List<DbInfo> threeList) {
        if (threeList != null && threeList.size() != 0) {
            threeMap.put(twoId, threeList);
            int len = threeList.size();
            int districtDefault = -1;
            if (!TextUtils.isEmpty(defaultDistrict) && len > 0) {
                for (int i = 0; i < len; i++) {
                    if (threeList.get(i).getText().contains(defaultDistrict)) {
                        districtDefault = i;
                        break;
                    }
                }
            }
            String[] areas = new String[len];
            for (int i = 0; i < len; i++) {
                areas[i] = threeList.get(i).getText();
            }

            districtWheel = new ArrayWheelAdapter<String>(context, areas);
            // 设置可见条目数量
            districtWheel.setTextColor(textColor);
            districtWheel.setTextSize(textSize);
            districtWheel.setCheckedTextColor(selecterColor);
            threeView.setViewAdapter(districtWheel);
            if (-1 != districtDefault) {
                threeView.setCurrentItem(districtDefault);
                //获取默认设置的区
                threeName = defaultDistrict;
            } else {
                threeView.setCurrentItem(0);
                //获取第一个区名称
                threeName = threeMap.get(twoId).get(0).getText();
                threeId = threeMap.get(twoId).get(0).getId();
            }
            districtWheel.setPadding(padding);

        } else {
            if (districtWheel != null) {
                districtWheel.clear();
            }

        }

    }


    private ArrayWheelAdapter cityWheel;

    /**
     * 显示二级目录
     */
    private void updateCities(List<DbInfo> two) {
        //  一级目录选中的位置
        if (two != null && two.size() != 0) {
            int cityDefault = -1;
            int len = two.size();
            if (!TextUtils.isEmpty(defaultCityName) && len > 0) {
                for (int i = 0; i < len; i++) {
                    if (two.get(i).getText().contains(defaultCityName)) {
                        cityDefault = i;
                        break;
                    }
                }
            }

            String[] cities = new String[len];
            for (int i = 0; i < len; i++) {
                cities[i] = two.get(i).getText();
            }

            cityWheel = new ArrayWheelAdapter<String>(context, cities);
            // 设置可见条目数量
            cityWheel.setTextColor(textColor);
            cityWheel.setTextSize(textSize);
            cityWheel.setCheckedTextColor(selecterColor);
            twoView.setViewAdapter(cityWheel);
            if (-1 != cityDefault) {
                twoView.setCurrentItem(cityDefault);
            } else {
                twoView.setCurrentItem(0);
            }

            cityWheel.setPadding(padding);

            setThree();

        } else {
            if (cityWheel != null) {
                cityWheel.clear();
            }

        }

    }


    @Override
    public void setType(int type) {
    }

    private MyAsyncTask myAsyncTask;

    @Override
    public void show() {
        if (!isShow()) {
            setUpData();
            popwindow.showAtLocation(popview, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void hide() {
        if (isShow()) {
            popwindow.dismiss();
        }
    }

    @Override
    public boolean isShow() {
        return popwindow.isShowing();
    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == oneView) {
            setTwo();
            arrayWheelAdapter.setIndex(oneView.getCurrentItem());
        } else if (wheel == twoView) {
            setThree();
            cityWheel.setIndex(twoView.getCurrentItem());
        } else if (wheel == threeView) {
            threeName = threeMap.get(twoId).get(newValue).getText();
            threeId = threeMap.get(twoId).get(newValue).getId();
            districtWheel.setIndex(threeView.getCurrentItem());
        }
    }


    private static GetDataFromUrl getDataFromUrl;

    public static void setGetDataFromUrl(GetDataFromUrl getDataFromUrl) {
        PickerFromUrl.getDataFromUrl = getDataFromUrl;
    }


    public interface GetDataFromUrl {
        Key getFromUrlTwo(int keyId);

        Key getFromUrlThree(int oneId, int twoId);
    }


}
