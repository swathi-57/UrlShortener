package com.example.URLShortenerService.service;

import com.example.URLShortenerService.entity.ShortUrl;
import com.example.URLShortenerService.repository.ShortUrlRepository;
import com.example.URLShortenerService.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UrlShortenerService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private static final int MAX_COLLISION_RETRIES = 10;

    public UrlShortenerService(ShortUrlRepository shortUrlRepository, 
                              ShortCodeGenerator shortCodeGenerator) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }

    /**
     * Creates a short URL for the given original URL.
     * If the URL already exists, returns the existing ShortUrl.
     * Otherwise, generates a unique short code and saves it to the database.
     * 
     * @param originalUrl The original URL to shorten
     * @return The ShortUrl entity (existing or newly created)
     */
    public ShortUrl createShortUrl(String originalUrl) {
        // Check if URL already exists
        Optional<ShortUrl> existingUrl = shortUrlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get();
        }

        // Generate unique short code, handling collisions
        String shortCode = generateUniqueShortCode();

        // Create and save new ShortUrl
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(shortCode);

        return shortUrlRepository.save(shortUrl);
    }

    /**
     * Generates a unique short code by handling potential collisions.
     * 
     * @return A unique short code that doesn't exist in the database
     */
    private String generateUniqueShortCode() {
        int attempts = 0;
        String shortCode;

        do {
            shortCode = shortCodeGenerator.generate();
            attempts++;

            if (attempts > MAX_COLLISION_RETRIES) {
                throw new RuntimeException("Failed to generate unique short code after " + 
                    MAX_COLLISION_RETRIES + " attempts");
            }
        } while (shortUrlRepository.findByShortCode(shortCode).isPresent());

        return shortCode;
    }

    /**
     * Retrieves the original URL for a given short code.
     * 
     * @param shortCode The short code to lookup
     * @return Optional containing the original URL if found, empty otherwise
     */
    @Transactional(readOnly = true)
    public Optional<String> getOriginalUrl(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode)
                .map(ShortUrl::getOriginalUrl);
    }
}

