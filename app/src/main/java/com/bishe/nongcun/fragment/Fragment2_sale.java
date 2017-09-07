package com.bishe.nongcun.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.OKActivity;
import com.bishe.nongcun.adapter.ImagePickerAdapter;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.imageloader.GlideImageLoader0;
import com.bishe.nongcun.utils.ChooseCityInterface;
import com.bishe.nongcun.utils.ChooseCityUtil;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.view.LoadDialog;
import com.bishe.nongcun.view.SelectDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by 郑卫超 on 2017/5/3.
 * 发布供应
 */

public class Fragment2_sale extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private TextView tvCity;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数

    private String kind1;
    private String kind2;
    private String kind3;
    private EditText et_desc;
    private EditText et_title;
    private EditText et_price;
    private Button bt_enter;
    private Spinner sp_unit;
    private String priceUnit = "/斤";
    private ArrayList<String> photos;
    String[] filePaths = new String[3];
    private String title;
    private String price;
    private String desc;

    @Override
    protected View initView() {
        initImagePicker();
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_2_push, null);
        tvCity = (TextView) inflate.findViewById(R.id.tv_select_mou);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView0);
        et_desc = (EditText) inflate.findViewById(R.id.et_push_sale_desc);
        et_title = (EditText) inflate.findViewById(R.id.et_push_sale_title);
        et_price = (EditText) inflate.findViewById(R.id.et_push_sale_price);
        bt_enter = (Button) inflate.findViewById(R.id.bt_push_sale_enter);
        sp_unit = (Spinner) inflate.findViewById(R.id.sp_push_sale_unit);


        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mActivity, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return inflate;
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader0());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    public void initData() {

        //        种类选择
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCityUtil cityUtil = new ChooseCityUtil();
                String[] oldCityArray = {"水果", "苹果", "苹果"};
                cityUtil.createDialog(mActivity, oldCityArray, new ChooseCityInterface() {
                    @Override
                    public void sure(String[] newCityArray) {
                        //oldCityArray为传入的默认值 newCityArray为返回的结果
                        tvCity.setText(newCityArray[0] + "-" + newCityArray[1] + "-" + newCityArray[2]);

                        kind1 = newCityArray[0];
                        kind2 = newCityArray[1];
                        kind3 = newCityArray[2];

                    }
                });
            }
        });
    }


    @Override
    public void initListener() {
        sp_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] units = getResources().getStringArray(R.array.units);
                priceUnit = units[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                desc = et_desc.getText().toString().trim();
                price = et_price.getText().toString().trim();
                title = et_title.getText().toString().trim();

                if (TextUtils.isEmpty(desc) || TextUtils.isEmpty(price) || TextUtils.isEmpty(title)) {
                    Toast.makeText(mActivity, "请您完整地填写信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(kind1)) {
                    Toast.makeText(mActivity, "请选择分类", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selImageList.size() != 3) {
                    Toast.makeText(mActivity, "请选择3张图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                LogUtils.e("开始压缩……");

                ArrayList<String> paths = new ArrayList<>();
                paths.add(selImageList.get(0).path);
                paths.add(selImageList.get(1).path);
                paths.add(selImageList.get(2).path);
                initLuban(paths);

            }
        });

    }

    private void initLuban(List<String> paths) {
        photos = new ArrayList<>();
        Luban.with(mActivity)
                .load(paths)                                   // 传入要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        LoadDialog.show(mActivity,"正在进行图片压缩ing……");
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        LogUtils.e("压缩成功");
                        LoadDialog.dismiss(mActivity);

//                        Toast.makeText(mActivity, "压缩成功！", Toast.LENGTH_SHORT).show();
                        photos.add(file.getAbsolutePath());
                        if (photos.size()==3){
                            filePaths[0]=photos.get(0) ;
                            filePaths[1]=photos.get(1) ;
                            filePaths[2]=photos.get(2) ;
                            BmobUpSale();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        LoadDialog.dismiss(mActivity);
                        Toast.makeText(mActivity, "压缩失败！", Toast.LENGTH_SHORT).show();
                    }
                }).launch();    //启动压缩

    }

    private void BmobUpSale() {
        LoadDialog.show(mActivity, "发布供应中...");
        LogUtils.e("开始上传……");
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                // TODO Auto-generated method stub
                Log.i("life", "insertDataWithMany -onSuccess :" + urls.size() + "-----" + files + "----" + urls);
                if (urls.size() == 3) {//如果全部上传完，则更新该条记录
                    PriceItem priceItem = new PriceItem();
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    priceItem.setAuthor(user);
                    priceItem.setTitle(title);
                    priceItem.setContent(desc);
                    priceItem.setPrice(price + "元" + priceUnit);
                    priceItem.setKind1(kind1);
                    priceItem.setKind2(kind2);
                    priceItem.setKind3(kind3);
                    priceItem.setPic1(files.get(0));
                    priceItem.setPic2(files.get(1));
                    priceItem.setPic3(files.get(2));
                    priceItem.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {

                            if (e == null) {
                                LogUtils.e("上传成功……");
                                Toast.makeText(mActivity, "创建数据成功", Toast.LENGTH_SHORT).show();
                                LogUtils.e("创建数据成功：" + objectId);
                                initNull();
                                Intent intent = new Intent(mActivity, OKActivity.class);
                                startActivity(intent);
                                LoadDialog.dismiss(mActivity);
                            } else {
                                LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                                LoadDialog.dismiss(mActivity);
                            }
                        }
                    });

                } else {
                    //有可能上传不完整，中间可能会存在未上传成功的情况，你可以自行处理
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                LogUtils.e("错误码" + statuscode + ",错误描述：" + errormsg);
                Toast.makeText(mActivity, "发生错误，请稍后重试", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub
                LoadDialog.dismiss(mActivity);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                Log.i("life", "insertBatchDatasWithOne -onProgress :" + curIndex + "---" + curPercent + "---" + total + "----" + totalPercent);
                // TODO Auto-generated method stub
                LoadDialog.dismiss(mActivity);
            }


        });

    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void initNull() {
        et_desc.setText("");
        et_title.setText("");
//        et_unit.setText("");
        et_price.setText("");
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(mActivity, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!mActivity.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(mActivity, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(mActivity, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(mActivity, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }

    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path = images.get(0).path;
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

}
