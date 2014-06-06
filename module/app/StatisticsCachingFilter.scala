/**
 * Created with IntelliJ IDEA.
 * User: ashik.salahudeen
 * Date: 11/19/13
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */
package RouteStatistics

import play.api.mvc._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class StatisticsCachingFilter extends Filter {

  def apply(nextFilter: (RequestHeader) => Future[SimpleResult])
           (requestHeader: RequestHeader): Future[SimpleResult] = {

    val startTime = System.currentTimeMillis
    nextFilter(requestHeader).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime
      // put into frigging cache
      RouteStatistics.addToStatsCache(requestHeader.uri,result.header.status,requestTime)
      //Logger.info(s"${requestHeader.method} ${requestHeader.uri} " + s"took ${requestTime}ms and returned ${result.header.status}")
      result.withHeaders("Request-Time" -> requestTime.toString)
    }
  }
}