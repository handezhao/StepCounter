## StepCounter
---
### OverView
* 效果图   
![github](https://github.com/handezhao/StepCounter.git/raw/master/picture/stepview.png)
* 实现思路  
![github](https://github.com/handezhao/StepCounter.git/raw/master/picture/xmind.png)    

### Usage
> 通过StepCounter添加SensorChangeListener的监听   
> 使用自定的StepView展示实时的步数

```java
SensorChangeListener sensorChangeListener = new SensorChangeListener() {
            @Override
            public void onSensorChanged(final int var, boolean useStepCounter) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stepView.setSteps(var, 5000);
                    }
                });
            }
        };
StepCounter.getInstance().addSensorChangedListener(sensorChangeListener);
```

### How it works
* Sensor.TYPE_ACCELEROMETER   
    > 加速度传感器，通过判断x、y、z三轴方向上的加速度大小是否超过某个阈值来判断是否移动了，几乎所有的Android手机都有这个传感器，但是它很容易被Android系统kill掉，耗电严重。

* Sensor.TYPE_STEP_COUNTER
    > 谷歌新出的用来记录步数的传感器，它记录的是一个总数，值会在关机后清零，需要特殊的传感器，并且它需要API在19以上，优点是它可以在后台计步，经测试市面上大部分的计步应该都是用的这个传感器

* 我的实现方式
    > 优先使用 TYPE_STEP_COUNTER，如果无法使用则选择 TYPE_ACCELEROMETER，
