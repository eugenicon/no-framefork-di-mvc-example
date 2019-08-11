package com.conference.controller;

import com.conference.Component;
import com.conference.dao.OrderDao;
import com.conference.service.AuthenticatedUser;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;

@Component
@Controller
public class OrderController {
    private final OrderDao orderDao;

    public OrderController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @GetMapping("/orders")
    public View showWelcomePage(AuthenticatedUser authenticatedUser) {
        return View.of("order/order-list.jsp").with("list", orderDao.findAllByUserId(authenticatedUser.getId()));
    }

}
