package com.liupeng.webservice;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.liupeng.vo.Student;

/**
 * @author fengdao.lp
 * @date 2018/7/19
 */
@WebService
@Path("/student")
public interface StudentService {
    /**
     * 查询学生信息
     * http://127.0.0.1:12345/rest/student/query/1
     */
    @GET
    @Path("/query/{id}")
    @Produces(MediaType.APPLICATION_XML)
    Student queryStudent(@PathParam("id") long id);

    @GET
    @Path("/querylist/{type}")
    @Produces({"application/json;charset=utf-8", MediaType.APPLICATION_XML})
        //设置媒体类型xml格式和json格式
        //如果想让rest返回xml需要在rest的url后边添加?_type=xml，默认为xml
        //如果想让rest返回json需要在rest的url后边添加?_type=json
    List<Student> queryStudentList(@PathParam("type") String type);
}
