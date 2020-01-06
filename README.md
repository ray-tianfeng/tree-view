## 树形自定义View
- **自定义属性通过config来配置视图属性**
- **节点注释**
- **截图接口**  
 
   
### 用法 ###

-  LIB配置</br> 
```gradle 
allprojects { repositories { jcenter() maven { url
   'https://jitpack.io' } } }

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.github.ray-tianfeng:tree-view:v1.0.2'
}
```

- 配置属性（TreeConfig）
<table>
 <tr>
  <td>参数</td>
  <td>是否必须</td>
  <td>说明</td>
 </tr>
 <tr>
  <td>trunkWidth</td>
  <td>N</td>
  <td>主干水平方向节点间间隙宽度</td>
 </tr>
 <tr>
  <td>trunkHeight</td>
  <td>N</td>
  <td>主干竖直方向节点间间隙高度</td>
 </tr>
 <tr>
  <td>leafWidth</td>
  <td>N</td>
  <td>树叶与主干间水平方向间隙宽度</td>
 </tr>
 <tr>
  <td>leafHeight</td>
  <td>N</td>
  <td>树叶与主干间水平方向的高度</td>
 </tr>
 <tr>
  <td>paddingWidth</td>
  <td>N</td>
  <td>节点在宽度上的padding</td>
 </tr>
 <tr>
  <td>paddingWidth</td>
  <td>N</td>
  <td>节点在宽度上的padding</td>
 </tr>
 <tr>
  <td>paddingHeight</td>
  <td>N</td>
  <td>节点在高度上的padding</td>
 </tr>
 <tr>
  <td>textSize</td>
  <td>N</td>
  <td>节点文字大小，描述文字大小=textSize - 5</td>
 </tr>
 <tr>
  <td>interval_state</td>
  <td>N</td>
  <td>树和状态间的间隙</td>
 </tr>
 <tr>
  <td>state_margin_top</td>
  <td>N</td>
  <td>状态与顶部的间距</td>
 </tr>
 <tr>
  <td>backGroundColor</td>
  <td>N</td>
  <td>树形图背景颜色</td>
 </tr>
 </table>

- 数据配置 

**TreeData**
<table>
 <tr>
  <td>参数</td>
  <td>是否必须</td>
  <td>说明</td>
 </tr>
 <tr>
  <td>treeName</td>
  <td>Y</td>
  <td>树名字</td>
 </tr>
 <tr>
  <td>state</td>
  <td>N</td>
  <td>状态</td>
 </tr>
 <tr>
  <td>des</td>
  <td>N</td>
  <td>描述</td>
 </tr>
  <tr>
  <td>trunks</td>
  <td>Y</td>
  <td>横向主干</td>
 </tr>
 </table>
 
 **Trunk**
<table>
 <tr>
  <td>参数</td>
  <td>是否必须</td>
  <td>说明</td>
 </tr>
 <tr>
  <td>trunkName</td>
  <td>Y</td>
  <td>横向主干节点名称</td>
 </tr>
 <tr>
  <td>state</td>
  <td>N</td>
  <td>状态</td>
 </tr>
 <tr>
  <td>des</td>
  <td>N</td>
  <td>描述</td>
 </tr>
  <tr>
  <td>branches</td>
  <td>Y</td>
  <td>纵向枝干集合</td>
 </tr>
 </table>
 
  **Branch**
<table>
 <tr>
  <td>参数</td>
  <td>是否必须</td>
  <td>说明</td>
 </tr>
 <tr>
  <td>branchName</td>
  <td>Y</td>
  <td>纵向枝干节点名称</td>
 </tr>
 <tr>
  <td>state</td>
  <td>N</td>
  <td>状态</td>
 </tr>
 <tr>
  <td>des</td>
  <td>N</td>
  <td>描述</td>
 </tr>
  <tr>
  <td>leafs</td>
  <td>N</td>
  <td>树叶集合</td>
 </tr>
 </table>
 
 **Leaf**
<table>
 <tr>
  <td>参数</td>
  <td>是否必须</td>
  <td>说明</td>
 </tr>
 <tr>
  <td>leafName</td>
  <td>Y</td>
  <td>树叶节点名称</td>
 </tr>
 <tr>
  <td>state</td>
  <td>N</td>
  <td>状态</td>
 </tr>
 <tr>
  <td>des</td>
  <td>N</td>
  <td>描述</td>
 </tr>
 </table>
 
-  说明   
   树会每秒刷新，如果有数据更新，绘制的数据进行更新。描述状态在自动刷新的逻辑不会更改，只有当点击空白处和拖动树形图时才会刷新状态。背景颜色的设置实现显示的颜色，分享时默认背景颜色为白色。

-  方法
<table>
 <tr>
  <td>方法</td>
  <td>说明</td>
 </tr>
 <tr>
  <td>setFocusableInTouchMode</td>
  <td>设置是否可操作，true可拖动点击节点和显示描述，false不可操作，点击或者落地事件分发给遮挡视图下的第一个视图</td>
 </tr>
 <tr>
  <td>screenshots</td>
  <td>截图，返回一个bitmap</td>
 </tr>
</table>
 ![示例](https://github.com/ray-tianfeng/tree-view/blob/master/img/design.jpg)
