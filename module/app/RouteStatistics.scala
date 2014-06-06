/**
 * Created with IntelliJ IDEA.
 * User: ashik.salahudeen
 * Date: 11/19/13
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */



import play.api.cache.Cache
import play.api.libs.json.Json
import play.api.Play._
import play.Logger
import scala.collection.immutable.HashMap


package object RouteStatistics {

  val successCacheName = "STATSCACHE_SUCCESS"
  val failureCacheName = "STATSCACHE_FAILURE"

  case class RouteStatistic(routeName: String,successRequestCount: Long,failureRequestCount: Long,
                            averageSuccessResponseTime: Double,averageFailureResponseTime: Double,
                            medianSuccessResponseTime: Long,medianFailureResponseTime: Long)

  def setIntoCache(cacheType:String,key:String,value:Object){ Cache.set(cacheType+key,value)}
  def getFromCache(cacheType:String,key:String) =  Cache.get(cacheType+key)
  def getStringFromCache(cacheType:String,key:String) =  Cache.getAs[String](cacheType+key).getOrElse("")


  def addToStatsCache(requestHeaderURI:String,result:Int,requestTime:Long){
    val statscacheName = if(result == 200){successCacheName}else{failureCacheName}
    val stats:Array[(Long,Long)] = Cache.getOrElse[Array[(Long,Long)]](statscacheName+requestHeaderURI){
      Array((0,0))
    }
    Cache.set(statscacheName+requestHeaderURI,stats ++ Array((stats.length,requestTime)))
  }

  // return all routes except the root
  def getRoutes() = {
    for {
      routes <- play.api.Play.current.routes.toList
      (method, pattern, call) <- routes.documentation
      if !pattern.equals("/")
    } yield {
      pattern
    }
  }

  def getValidRoutes() = {
    // get a list of urls to check for stats

    val routes = getRoutes()
    val invalid_routes = List("/assets")
    // member of routes does not start with any of the strings in the list of invalid routes
    val valid_routes = invalid_routes.foldLeft(routes){(r,c) =>
      r.filter(p => (!p.startsWith(c)))
    }
    valid_routes
  }

  def calculateStats(stats:Array[(Double,Long)]) = {
    //   number of success request -> length of array minus one
    //   get median response time

    val times = stats.foldLeft(List[Long]()){(r,c) => c._2 :: r }
    val sum = times.foldLeft(0 toLong ){ _ + _ }
    val avg = if(sum==0) 0.0 else {
      sum / (stats.length-1)
    }
    val median = if(sum==0) 0 toLong else times.sorted.apply((times.size - 1) / 2)
    //   get average response time
    (stats.length-1 toLong,avg,median)
  }
  def calculateRouteStat(route: String) = {
    val success_stats = calculateStats(
      Cache.getOrElse[Array[(Double,Long)]]("STATSCACHE_SUCCESS"+route){Array((0.0,0L))}
    )
    val failure_stats = calculateStats(
      Cache.getOrElse[Array[(Double,Long)]]("STATSCACHE_FAILURE"+route){Array((0.0,0L))}
    )
    RouteStatistic(route.toString,
      success_stats._1,failure_stats._1,
      success_stats._2,failure_stats._2,success_stats._3,failure_stats._3)
  }

  def calculateRouteStats() = getValidRoutes.map(route => calculateRouteStat(route))

  def clearStatsCache() { // method with side effect not a function call
  val valid_routes = getValidRoutes()
    valid_routes.map(route => {
      // clear cache
      Cache.remove(successCacheName+route)
      Cache.remove(failureCacheName+route)
    })
  }
}


