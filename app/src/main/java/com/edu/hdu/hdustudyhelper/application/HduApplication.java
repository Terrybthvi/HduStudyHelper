package com.edu.hdu.hdustudyhelper.application;

import com.edu.hdu.hdustudyhelper.service.CourseService;
import com.edu.hdu.hdustudyhelper.service.LinkService;
import org.litepal.LitePalApplication;


/**
 * Created by leiqi on 2017/3/12.
 */

public class HduApplication extends LitePalApplication {
    private CourseService courseService;
    private LinkService linkService;
    @Override
    public void onCreate() {
        super.onCreate();
        courseService = new CourseService();
        linkService = new LinkService();
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public LinkService getLinkService() {
        return linkService;
    }

}
