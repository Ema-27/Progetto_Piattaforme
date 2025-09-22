package com.educative.ecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Component
@Transactional
public class DataSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbc;

    public DataSeeder(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se esiste già la categoria "Folk"
        Integer countFolk = jdbc.queryForObject(
                "SELECT COUNT(*) FROM categories WHERE category_name = ?",
                Integer.class, "Folk");

        long categoryId;
        if (countFolk != null && countFolk > 0) {
            // prendi l'id esistente
            Map<String, Object> row = jdbc.queryForMap(
                    "SELECT id FROM categories WHERE category_name = ? LIMIT 1", "Folk");
            categoryId = ((Number) row.get("id")).longValue();
        } else {
            // inserisci la categoria Folk 
            KeyHolder kh = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO categories (category_name, description, image_url) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "Folk");
                ps.setString(2, "La musica folk, o popolare, è un genere musicale che affonda le sue radici nelle tradizioni e nella cultura di un popolo.");
                ps.setString(3, "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ2r40yC5RgdtoAGnjlbTVwxpInYIGrreXqsaaekPMd_-oKPMZYCv1PdLVQOUUX");
                return ps;
            }, kh);
            Number key = kh.getKey();
            if (key == null) throw new IllegalStateException("Impossibile ottenere id categoria");
            categoryId = key.longValue();
        }

        // Verifica se esiste già il prodotto con code "159"
        Integer countProd = jdbc.queryForObject(
                "SELECT COUNT(*) FROM products WHERE code = ?",
                Integer.class, "159");

        if (countProd == null || countProd == 0) {
            KeyHolder kh2 = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO products (code, description, image_url, name, price, version, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "159"); // code
                ps.setString(2, "Evviva le tarantelle"); // description
                ps.setString(3, "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ2r40yC5RgdtoAGnjlbTVwxpInYIGrreXqsaaekPMd_-oKPMZYCv1PdLVQOUUX"); // imageUrl
                ps.setString(4, "Stilla chjara"); // name
                ps.setDouble(5, 11.0); // price
                ps.setLong(6, 0L); // version
                ps.setLong(7, categoryId); // category_id FK
                return ps;
            }, kh2);
        }

        System.out.println("DataSeeder: seeding completato (categoria Folk e prodotto).");
    }
}