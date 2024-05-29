//package com.example.semester_6_oop_lab_1_back.controller.user;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.univ.oop.lab1.lab1.Util.Utils;
//import com.univ.oop.lab1.lab1.dao.UsersDAO;
//import com.univ.oop.lab1.lab1.model.Users;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//@WebServlet("/register")
//public class UserRegistrationServlet extends HttpServlet {
//    private final UsersDAO userDao = new UsersDAO();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String name = request.getParameter("name");
//        String password = request.getParameter("password");
//        Double money = Double.parseDouble(request.getParameter("money"));
//        Users user = new Users(Utils.randomId(), name, money, password);
//        userDao.save(user);
//        Map<String, Object> result = new HashMap<>();
//        result.put("success", true);
//        response.setContentType("application/json");
//        Gson gson = new GsonBuilder().create();
//        String json = gson.toJson(result);
//        PrintWriter out = response.getWriter();
//        out.print(json);
//        out.flush();
//    }
//}
//
//
