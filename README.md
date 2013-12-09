# ZHighCharts

## About

ZHighCharts is a ZK component designed to include Highcharts JS Library within a ZK application using pure Java code.

See the smalltalk [on this link](http://books.zkoss.org/wiki/Small_Talks/2012/November/ZHighCharts:_Integrating_ZK_with_Highcharts).

## Demo

Demonstrations and other stuff are available on this link: [http://zhighcharts.appspot.com](http://zhighcharts.appspot.com)

## Download

See [Downloads section](https://github.com/NGI-Maghreb/ZK/downloads) to download the latest version of ZHighCharts.

## Licence
* [GPL](https://github.com/NGI-Maghreb/ZK/blob/master/zhighcharts/zkdoc/COPYING)
* To use ZHighCharts, a valid Highcharts license is required. Note that the use of Highcharts is free for non-profit organizations, students, universities, public schools and non-commercial personal websites. To use ZHighCharts for commercial services, a valid Highcharts license is required.
More information are available here: [Highcharts JS Licence](http://shop.highsoft.com/highcharts.html).

## Changelog
### 0.3
* Update HighCharts to 3.0.7
* add setExporting method to set [exporting](http://api.highcharts.com/highcharts#exporting) options
 ```
chartComp.setExporting("{enableImages:true,filename:'myChartname',url:'http://localhost:8080/highcharts-export/Highcharts-Chart-Export',width:2000"}");
 ```
* add setTitleOptions method to set [title](http://api.highcharts.com/highcharts#title) options
 ```
chartComp.setTitleOptions("{style:{color: 'red',fontSize: '20px'},text : 'Monthly Average Temperature'}"); 
```
* add setSubtitleOptions method to set [subtitle](http://api.highcharts.com/highcharts#subtitle) options
 ```
chartComp.setSubtitleOptions("{style:{color: 'green',fontSize: '12px'},text : 'Source: WorldClimate.com'}");
 ```
### 0.2
* Initial commit

## FAQ
**1. Does ZHighCharts support MVVVM?**

No.

**2. Which version of HighCharts is used?**

A custom version 3.0.7 : the merge function caused a "too much recursion" on Firefox / "Maximum call stack size exceeded." on Chrome, so we replaced it with the old one.

**3. How to compile and run ZHighCharts demo?**

Using Maven: `mvn compile package jetty:run` then browse to [http://localhost:9090/test/](http://localhost:9090/test/)

**4. Which version of ZK is ZHighCharts compatible with?**

ZHighCharts is compatible with ZK 5.0.11+ (Tested with 6.5.4 and 7.0.0-Preview).

