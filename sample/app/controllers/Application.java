package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.JavaConversions;
import scala.collection.immutable.List;
import views.html.index;
import RouteStatistics.*;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready.",RouteStatistics.package$.MODULE$.calculateRouteStats()));
    }
    public static Result index1(){
        return ok("OK");
    }
}
