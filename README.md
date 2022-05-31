# LeafletDroid


Leaflet SDK for android platform

[leaflet](https://leafletjs.com/) 引擎的android端封装实现，用于加载常见格式的栅格瓦片

### 实现了：

- [x] 坐标原点及切片分辨率的自定义
- [x] 坐标投影类型的自定义
- [x] 支持常见栅格瓦片如WMS,WMTS,TMS,ARCGIS等
- [x] 支持基础的地图功能操作，如Marker绘制，点线面绘制，视角切换，事件回调，热力图等功能
- [x] 功能逐步增加中

### 如何使用(可参考项目app模块的demo应用)

##### 将项目中的lib模块导入项目依赖

```
implementation project(path: ':lib')
```

##### 在页面新建创建`MapView`布局

```xml
<com.flo.leaflet.sdk.lib.MapView
    android:id="@+id/mapview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```

##### 通过`getMapAsync`方法获取`LeafletMap`实例

```kotlin
mapView.getMapAsync(object : OnMapReadyListener {
    override fun mapReady(map: LeafletMap) {
    }
}
```

##### 通过 `map.addLayer`方法添加瓦片图层

```kotlin
map.addLayer(
    TMSTile(
        "http://t2.tianditu.gov.cn/DataServer?T=vec_c&x={x}&y={y}&l={z}&tk=256f647aaffe22c1da0f4c0cae2dd806",
        id = "bg"
    )
)
```


