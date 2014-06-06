/**
 * Created by ashik.salahudeen on 6/06/2014.
 */

import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import RouteStatistics.StatisticsCachingFilter;

public class Global extends GlobalSettings {
    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{StatisticsCachingFilter.class};
    }
}