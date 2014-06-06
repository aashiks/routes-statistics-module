### Introduction

This Play Framework 2.2.x module is a quick and dirty hack to gather statistics on the number of times a route is accessed. I haven't tested it other than in very specific use case scenarios(trying to murder an app with Jmeter for hours and gather statistic)

The module provides a filter called StatisticsCachingFilter. 

This guy will intercept every incoming request, note the request time and time at which response it returned. 
At this point it records these values in the cache.

The scala class (ugh, package object) "RouteStatistics" provides methods to query this cache and return all route statistics as a list of RouteStatistic class

The definition of RouteStatistic is as below.

    case class RouteStatistic(routeName: String,
                                successRequestCount:Long, failureRequestCount: Long,
                                averageSuccessResponseTime: Double,averageFailureResponseTime: Double,
                                medianSuccessResponseTime: Long,medianFailureResponseTime: Long)


The usage is very "sillily" illustrated in the sample code. 

Anticipated bugs: 
  
It uses play's cache and stores two values per route per request. You do the math.
If you use it in a long running application with high usage, it may hit the limits.


####  USAGE

You only really need the module directory. 

clone it, and do a 

    play clean publish publish-local

This publishes it to both play repository (ivy) as well as maven repository on your local. Its a habit I picked up recently.

Look in the sample directory please


### License

The code is licensed under WTFPL. Feel free to modify it whichever way you want. Look in the build.sbt file to modify the org name and so on. 

### Bro Credits

Thanks to Minion, Dong&KP(for the missplellingses) and Mister Teh.

Also http://mfizz.com/blog/2013/07/play-framework-module-maven-central.
