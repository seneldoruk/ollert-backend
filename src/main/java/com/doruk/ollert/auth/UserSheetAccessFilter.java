package com.doruk.ollert.auth;

import com.doruk.ollert.service.SheetPartService;
import com.doruk.ollert.service.SheetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserSheetAccessFilter extends OncePerRequestFilter {

    final SheetService sheetService;
    final SheetPartService sheetPartService;

    public UserSheetAccessFilter(SheetService sheetService, SheetPartService sheetPartService) {
        this.sheetService = sheetService;
        this.sheetPartService = sheetPartService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        if (req.getRequestURI().contains("auth") || req.getRequestURI().contains("h2-console")) {
            filterChain.doFilter(req, res);
            return;
        }


        Boolean canAccess = true;
        String username;
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        } catch (Exception e) {
            res.setStatus(401);
            return;
        }

        String[] uri = req.getRequestURI().split("/");
        String requested_path = uri[1];
        Long requested_id;


        if (uri.length > 2 && !uri[2].contains("all")) {
            if (uri[2].contains("?")) {
                requested_id = Long.parseLong(uri[2].substring(0, uri[2].indexOf("?")));
            } else {
                requested_id = Long.parseLong(uri[2]);
            }
            if (requested_path.equals("sheet")) {
                canAccess = sheetService.checkAuth(username, requested_id);
            } else if (requested_path.equals("sheetpart")) {
                canAccess = sheetPartService.checkAuth(username, requested_id);
            }
        }
        if (canAccess) {
            filterChain.doFilter(req, res);
        } else {
            res.setStatus(403);
        }
    }
}
