gradle 统一配置
参考：
https://blog.csdn.net/myboyer/article/details/79229151
https://blog.csdn.net/cai_iac/article/details/51850291
https://blog.csdn.net/wangshixu2015/article/details/80391591

Gradle使用详解：
https://blog.gokit.info/post/android-gradle-deep-dive/

blankj工具集
git：https://github.com/Blankj/AndroidUtilCode
说明：https://blankj.com/2016/07/31/android-utils-code/


点击效果说明
1. 按钮圆形波纹效果，比如标题栏左右按钮，音乐播放器按钮
换成 ImageButton 并增加 3 个属性
<ImageButton
    android:layout_width="45dp"
    android:layout_height="45dp"
    android:radius="50dp"
    android:background="?android:attr/selectableItemBackgroundBorderless"
    android:colorControlHighlight="@color/colorRipple"/>


2. 列表项按压、点击波纹效果
根布局增加 style RippleWhiteRect
<android.support.constraint.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:theme="@style/RippleWhiteRect">
....
</android.support.constraint.ConstraintLayout>


3. 图片按压变暗效果
图片控件更换为 DarkableRoundImageView，其他不变

<xiongdixingqiu.haier.com.xiongdixingqiu.view.DarkableRoundImageView/>
要想有点击效果，ImageView 需要有点击事件，你们可以用 helper.itemView.performClick()  转一下