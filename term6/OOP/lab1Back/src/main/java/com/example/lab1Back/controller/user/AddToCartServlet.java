//package com.example.semester_6_oop_lab_1_back.controller.user;
//
//import com.univ.oop.lab1.lab1.Util.Utils;
//import com.univ.oop.lab1.lab1.dao.UsersToursCartDAO;
//import com.univ.oop.lab1.lab1.model.UsersToursCart;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//
//@WebServlet("/add-to-cart")
//public class AddToCartServlet extends HttpServlet {
//    private final UsersToursCartDAO usersToursCartDAO = new UsersToursCartDAO();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        int tourId = Integer.parseInt(request.getParameter("tourId"));
//
//        UsersToursCart usersToursCart = new UsersToursCart(Utils.randomId(), userId, tourId);
//        usersToursCartDAO.save(usersToursCart);
//        PrintWriter out = response.getWriter();
//        out.print("Added to cart");
//        out.flush();
//    }
//}