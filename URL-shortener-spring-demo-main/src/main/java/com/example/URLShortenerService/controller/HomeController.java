package com.example.URLShortenerService.controller;

import com.example.URLShortenerService.entity.ShortUrl;
import com.example.URLShortenerService.model.ShortenUrlRequest;
import com.example.URLShortenerService.model.ShortenUrlResponse;
import com.example.URLShortenerService.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
public class HomeController {

    private final UrlShortenerService urlShortenerService;

    public HomeController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Displays the form page for URL shortening.
     */
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("request", new ShortenUrlRequest());
        return "index";
    }

    /**
     * Processes the form submission and creates a short URL.
     */
    @PostMapping("/shorten")
    public String shortenUrl(@Valid ShortenUrlRequest request, 
                            BindingResult bindingResult,
                            Model model,
                            HttpServletRequest httpRequest) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        ShortUrl shortUrl = urlShortenerService.createShortUrl(request.getUrl());
        
        // Build the short URL
        String baseUrl = getBaseUrl(httpRequest);
        String shortUrlString = baseUrl + "/" + shortUrl.getShortCode();
        
        // Create response object
        ShortenUrlResponse response = new ShortenUrlResponse();
        response.setShortCode(shortUrl.getShortCode());
        response.setShortUrl(shortUrlString);
        response.setOriginalUrl(shortUrl.getOriginalUrl());
        response.setCreatedAt(shortUrl.getCreatedAt());
        response.setExpiresAt(shortUrl.getExpiresAt());
        
        model.addAttribute("response", response);
        model.addAttribute("request", new ShortenUrlRequest()); // Reset form
        return "index"; // Return to index to show result on same page
    }

    /**
     * Redirects to the original URL based on the short code.
     */
    @GetMapping("/{shortCode}")
    public String redirectToOriginalUrl(@PathVariable String shortCode) {
        Optional<String> originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        
        if (originalUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short code not found");
        }
        
        return "redirect:" + originalUrl.get();
    }

    /**
     * Helper method to get the base URL from the request.
     */
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(serverName);
        
        // Only append port if it's not the default port
        if ((scheme.equals("http") && serverPort != 80) || 
            (scheme.equals("https") && serverPort != 443)) {
            baseUrl.append(":").append(serverPort);
        }
        
        baseUrl.append(contextPath);
        
        return baseUrl.toString();
    }
}

